package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity

class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart),
    ShoppingCartEventHandler {
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSubActivityMenuBar(getString(R.string.toolbar_title_cart), binding.toolbar)

        val shoppingApplication = application as ShoppingApplication
        val factory = ShoppingCartViewModel.createFactory(shoppingApplication.shoppingCartRepository)
        viewModel = ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]

        with(viewModel) {
            requestProductsPage(0)
            productsLiveData.observe(this@ShoppingCartActivity) { page ->
                val adapter = binding.rvShoppingCartList.adapter as ShoppingCartAdapter
                adapter.updateProducts(page.items)
            }
        }

        binding.apply {
            rvShoppingCartList.adapter = ShoppingCartAdapter(this@ShoppingCartActivity)
            viewModel = this@ShoppingCartActivity.viewModel
            handler = this@ShoppingCartActivity
        }
    }

    override fun onProductRemove(product: Product) {
        viewModel.removeProduct(product)
    }

    override fun onPaginationPrevious() {
        viewModel.requestProductsPage((viewModel.productsLiveData.value?.pageIndex ?: 0) - 1)
    }

    override fun onPaginationNext() {
        viewModel.requestProductsPage((viewModel.productsLiveData.value?.pageIndex ?: 0) + 1)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}
