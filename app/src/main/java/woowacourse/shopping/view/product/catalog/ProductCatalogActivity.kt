package woowacourse.shopping.view.product.catalog

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductCatalogBinding
import woowacourse.shopping.databinding.CustomToolbarShoppingCartBinding
import woowacourse.shopping.view.cart.ShoppingCartActivity
import woowacourse.shopping.view.product.catalog.adapter.ProductAdapter
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogItem
import woowacourse.shopping.view.product.detail.ProductDetailActivity

class ProductCatalogActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductCatalogBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        val app = application as ShoppingApplication
        ViewModelProvider(
            this,
            ProductCatalogViewModelFactory(
                app.productRepository,
                app.cartProductRepository,
                app.recentProductRepository,
            ),
        )[ProductCatalogViewModel::class.java]
    }

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        initRecyclerView()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadCatalog()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_shopping_cart, menu)

        val binding = CustomToolbarShoppingCartBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val menuItem = menu.findItem(R.id.shopping_cart)
        menuItem.actionView = binding.root
        menuItem.actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shopping_cart) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpView() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(eventHandler = viewModel)
        binding.rvProducts.adapter = productAdapter

        val gridLayoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        gridLayoutManager.spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (
                        ProductCatalogItem.ViewType.entries[
                            productAdapter.getItemViewType(
                                position,
                            ),
                        ]
                    ) {
                        ProductCatalogItem.ViewType.RECENT_PRODUCT -> GRID_SPAN_COUNT
                        ProductCatalogItem.ViewType.PRODUCT -> 1
                        ProductCatalogItem.ViewType.LOAD_MORE -> GRID_SPAN_COUNT
                    }
            }
        binding.rvProducts.layoutManager = gridLayoutManager
        binding.rvProducts.itemAnimator = null
    }

    private fun initObservers() {
        viewModel.productCatalogItems.observe(this) { value ->
            productAdapter.updateItems(value)
        }

        viewModel.selectedProduct.observe(this) { value ->
            val intent = ProductDetailActivity.newIntent(this, value)
            startActivity(intent)
        }
    }

    companion object {
        private const val GRID_SPAN_COUNT = 2
    }
}
