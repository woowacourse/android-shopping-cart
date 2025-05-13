package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.base.BaseActivity
import woowacourse.shopping.databinding.ActivityShoppingCartBinding
import woowacourse.shopping.domain.Product

class ShoppingCartActivity : BaseActivity<ActivityShoppingCartBinding>(R.layout.activity_shopping_cart) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
