package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.ui.cart.stateholder.rememberCartStateHolder
import woowacourse.shopping.ui.cart.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.state.ProductUiModel

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val stateHolder = rememberCartStateHolder(getCartItems(intent))
            AndroidshoppingcartTheme {
                CartScreen(
                    cartItems = stateHolder.cartItems,
                    onCloseClick = {
                        setResult(RESULT_OK, deletedListResult(stateHolder.deletedItemIds))
                        finish()
                    },
                    onDelete = { id ->
                        stateHolder.deleteCartItem(id)
                    },
                    isLeftEnable = stateHolder.isStartPage().not(),
                    onLeftClick = {
                        stateHolder.onLeftClick()
                    },
                    isRightEnable = stateHolder.isEndPage().not(),
                    onRightClick = {
                        stateHolder.onRightClick()
                    },
                    page = stateHolder.page,
                )
            }
        }
    }

    companion object {
        private const val EXTRA_CART_ITEMS = "extra_cart_items"
        private const val EXTRA_DELETED_LIST = "deleted_cart_list"

        fun newIntent(context: Context, cartItems: List<ProductUiModel>): Intent = Intent(context, CartActivity::class.java)
            .putParcelableArrayListExtra(EXTRA_CART_ITEMS, ArrayList(cartItems))

        fun getDeletedList(intent: Intent?): List<String>? = intent?.getStringArrayListExtra(EXTRA_DELETED_LIST)

        private fun getCartItems(intent: Intent): List<ProductUiModel> = intent.getParcelableArrayList(EXTRA_CART_ITEMS) ?: emptyList()

        private fun Intent.getParcelableArrayList(name: String): List<ProductUiModel>? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelableArrayListExtra(name, ProductUiModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                getParcelableArrayListExtra(name)
            }

        private fun deletedListResult(ids: List<String>): Intent = Intent().putStringArrayListExtra(EXTRA_DELETED_LIST, ArrayList(ids))
    }
}
