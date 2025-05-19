package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ShoppingCartActivityTemplate
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.page.Page
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import kotlin.getValue

class MainActivity :
    ShoppingCartActivityTemplate<ActivityMainBinding>(R.layout.activity_main),
    ProductsEventHandler {
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar as Toolbar)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            handler = this@MainActivity
        }
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_shopping_cart) {
            val intent = ShoppingCartActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerview() {
        binding.rvProductList.apply {
            adapter = ProductsAdapter(this@MainActivity)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        }
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@MainActivity) { page -> updateRecyclerView(page) }
        }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        binding.rvProductList.adapter.apply {
            (this as ProductsAdapter).updateProducts(page.items)
            notifyItemInserted(itemCount)
        }
    }

    override fun onProductSelected(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts(page: Int) {
        binding.btnLoadMoreProducts.visibility = View.GONE
        viewModel.requestProductsPage(page)
    }
}
