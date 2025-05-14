package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.getParcelableCompat

class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart),
    ShoppingCartEventHandler {
    private val viewModel: ShoppingCartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSubActivityMenuBar(getString(R.string.toolbar_title_cart), binding.toolbar)
        val product =
            intent.getParcelableCompat<Product>(KEY_PRODUCT) ?: run {
                onUnexpectedError(getString(R.string.error_product_is_null))
                return
            }
        viewModel.addProduct(product)
        val adapter = ShoppingCartAdapter(viewModel.products, this)
        viewModel.productsLiveData.observe(this) {
            adapter.updateProducts(it)
            binding.rvShoppingCartList.adapter?.notifyDataSetChanged()
        }
        binding.rvShoppingCartList.adapter = adapter
    }

    override fun onProductRemove(product: Product) {
        viewModel.removeProduct(product)
    }

    companion object {
        private const val KEY_PRODUCT = "product"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent {
            return Intent(context, ShoppingCartActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
