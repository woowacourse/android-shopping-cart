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
import woowacourse.shopping.databinding.ActivityProductCatalogBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.catalog.ProductAdapter.Companion.LOAD_MORE
import woowacourse.shopping.view.product.detail.ProductDetailActivity
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductCatalogActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductCatalogBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ProductCatalogViewModel

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.rootProductCatalog)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootProductCatalog) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel =
            ViewModelProvider(
                this,
                ProductCatalogViewModel.provideFactory(),
            )[ProductCatalogViewModel::class.java]

        initRecyclerView()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_shopping_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shopping_cart) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        productAdapter =
            ProductAdapter(
                productsEventListener = { product -> navigateToProductDetail(product) },
                loadEventListener = viewModel::loadProducts,
            )

        binding.rvProducts.adapter = productAdapter
        val gridLayoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        gridLayoutManager.spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (productAdapter.getItemViewType(position)) {
                        LOAD_MORE -> GRID_SPAN_COUNT
                        else -> 1
                    }
            }

        binding.rvProducts.layoutManager = gridLayoutManager
    }

    private fun initObservers() {
        viewModel.productItems.observe(this) { value ->
            productAdapter.addItems(value)
        }
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = ProductDetailActivity.newIntent(this, product)
        startActivity(intent)
    }

    companion object {
        private const val GRID_SPAN_COUNT = 2
    }
}
