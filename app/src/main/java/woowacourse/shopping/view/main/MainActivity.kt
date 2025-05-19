package woowacourse.shopping.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ShoppingCartActivityTemplate
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.page.Page
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import kotlin.getValue

class MainActivity :
    ShoppingCartActivityTemplate<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: ProductsViewModel by viewModels()
    private val handler: ProductsEventHandler by lazy {
        object : ProductsEventHandler {
            override fun onProductSelected(product: Product) {
                startActivity(ProductDetailActivity.newIntent(this@MainActivity, product))
            }

            override fun onLoadMoreProducts(page: Int) {
                binding.btnLoadMoreProducts.visibility = View.GONE
                viewModel.requestProductsPage(page)
            }
        }
    }
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(this@MainActivity.handler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar as Toolbar)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            handler = this@MainActivity.handler
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
        binding.productList.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addOnScrollListener(ProductsOnScrollListener(binding, viewModel))
        }
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@MainActivity) { page -> updateRecyclerView(page) }
        }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        productsAdapter.apply {
            updateProducts(page.items)
            notifyItemInserted(itemCount)
        }
    }
}

private class ProductsOnScrollListener(
    private val binding: ActivityMainBinding,
    private val viewModel: ProductsViewModel,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        binding.btnLoadMoreProducts.visibility =
            if (
                lastVisibleItemPosition == layoutManager.itemCount - 1 &&
                viewModel.totalSize > (recyclerView.adapter?.itemCount ?: 0)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
