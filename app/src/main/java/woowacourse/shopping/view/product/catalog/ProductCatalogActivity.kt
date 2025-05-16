package woowacourse.shopping.view.product.catalog

import android.content.Intent
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
import woowacourse.shopping.view.cart.ShoppingCartActivity
import woowacourse.shopping.view.product.catalog.adapter.ProductAdapter
import woowacourse.shopping.view.product.catalog.adapter.ProductAdapter.Companion.LOAD_MORE
import woowacourse.shopping.view.product.catalog.adapter.ProductCatalogEventHandler
import woowacourse.shopping.view.product.detail.ProductDetailActivity

class ProductCatalogActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductCatalogBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ProductCatalogViewModel.provideFactory(),
        )[ProductCatalogViewModel::class.java]
    }

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
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
            val intent = Intent(this, ShoppingCartActivity::class.java)
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
        productAdapter =
            ProductAdapter(
                eventHandler =
                    object : ProductCatalogEventHandler {
                        override fun onProductClick(item: Product) {
                            navigateToProductDetail(item)
                        }

                        override fun onMoreClick() {
                            viewModel.loadMoreProducts()
                        }
                    },
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
        viewModel.products.observe(this) { value ->
            productAdapter.updateItems(value, viewModel.hasNext)
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
