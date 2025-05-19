package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.common.DataBindingActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.ProductsAdapter.OnClickHandler

class ProductsActivity : DataBindingActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }
    private val productsAdapter: ProductsAdapter = ProductsAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewBinding()
        initObservers()
        viewModel.loadProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_products, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_cart) navigateToCart()
        return super.onOptionsItemSelected(item)
    }

    private fun createAdapterOnClickHandler() =
        object : OnClickHandler {
            override fun onProductClick(id: Int) {
                navigateToProductDetail(id)
            }

            override fun onIncreaseClick(id: Int) {
                viewModel.increaseCartProduct(id)
            }

            override fun onDecreaseClick(id: Int) {
                viewModel.decreaseCartProduct(id)
            }

            override fun onLoadMoreClick() {
                viewModel.loadProducts()
            }
        }

    private fun navigateToCart() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
    }

    private fun navigateToProductDetail(id: Int) {
        val intent = ProductDetailActivity.newIntent(this, id)
        startActivity(intent)
    }

    private fun initViewBinding() {
        binding.productItemsContainer.adapter = productsAdapter
        binding.productItemsContainer.layoutManager = ProductsLayoutManager(this, productsAdapter)
        binding.productItemsContainer.itemAnimator = null
    }

    private fun initObservers() {
        viewModel.catalogProducts.observe(this) { products ->
            productsAdapter.submitItems(products.products, products.hasMore)
        }
    }
}
