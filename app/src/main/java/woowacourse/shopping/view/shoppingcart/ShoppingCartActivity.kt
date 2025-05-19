package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.ShoppingCartActivityTemplate
import woowacourse.shopping.view.page.Page

class ShoppingCartActivity :
    ShoppingCartActivityTemplate<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart),
    ShoppingCartEventHandler {
    private val viewModel: ShoppingCartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSubActivityMenuBar(getString(R.string.toolbar_title_cart), binding.toolbar)
        viewModel.apply {
            requestProductsPage(0)
            productsLiveData.observe(this@ShoppingCartActivity) { page -> updateRecyclerView(page) }
        }
        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity)
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity
        }
    }

    private fun updateRecyclerView(page: Page<Product>) {
        binding.rvShoppingCartList.adapter.apply {
            val adapter = this as ShoppingCartAdapter
            val previousCount = itemCount
            adapter.updateProducts(page.items)
            notifyItemRangeChanged(0, previousCount)
        }
    }

    override fun onProductRemove(product: Product) {
        viewModel.removeProduct(product)
    }

    override fun onPaginationPrevious() {
        viewModel.requestProductsPage((viewModel.productsLiveData.value?.currentPage ?: 0) - 1)
    }

    override fun onPaginationNext() {
        viewModel.requestProductsPage((viewModel.productsLiveData.value?.currentPage ?: 0) + 1)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivityTemplate::class.java)
        }
    }
}
