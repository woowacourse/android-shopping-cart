package woowacourse.shopping.view.shoppingcart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.getParcelableCompat

class ShoppingCartActivity :
    BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart), ShoppingCartEventHandler {
    private val viewModel: ShoppingCartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMenuBar()
        val product =
            intent.extras?.getParcelableCompat<Product>(KEY_PRODUCT)
                ?: throw IllegalArgumentException(ERR_PRODUCT_IS_NULL)
        viewModel.addProduct(product)
        viewModel.products.observe(this) {
            binding.rvShoppingCartList.adapter?.notifyDataSetChanged()
        }
        binding.rvShoppingCartList.adapter = ShoppingCartAdapter(viewModel.products.value!!, this)
    }

    private fun initMenuBar() {
        val menuBar = binding.toolbar as Toolbar
        menuBar.menu.findItem(R.id.menu_item_shopping_cart).isVisible = false
        setSupportActionBar(menuBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Cart"
        }
    }

    companion object {
        private const val KEY_PRODUCT = "product"
        private const val ERR_PRODUCT_IS_NULL = "상품 정보가 로드되지 못했습니다"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent {
            return Intent(context, ShoppingCartActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }

    override fun onProductRemove(product: Product) {
        viewModel.removeProduct(product)
    }
}
