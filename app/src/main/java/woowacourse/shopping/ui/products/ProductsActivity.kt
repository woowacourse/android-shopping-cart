package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.ui.base.BaseActivity
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.ProductAdapter.OnClickHandler

class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter: ProductAdapter = ProductAdapter(createAdapterOnClickHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup =
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = productAdapter.getItemViewType(position)
                            return when (ItemViewType.entries[viewType]) {
                                ItemViewType.PRODUCT -> 1
                                ItemViewType.LOAD_MORE -> 2
                            }
                        }
                    }
            }

        viewModel.products.observe(this) { products ->
            productAdapter.updateProductItems(products)
            viewModel.updateIsLoadable()
        }
        viewModel.isLoadable.observe(this) { isLoadable ->
            productAdapter.updateLoadMoreItem(isLoadable)
        }

        viewModel.updateProducts(20)
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

            override fun onLoadMoreClick() {
                viewModel.updateProducts(20)
            }
        }

    private fun navigateToProductDetail(id: Int) {
        val intent = ProductDetailActivity.newIntent(this, id)
        startActivity(intent)
    }

    private fun navigateToCart() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
    }
}
