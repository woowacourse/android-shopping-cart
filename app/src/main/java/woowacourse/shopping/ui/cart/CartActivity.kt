package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import woowacourse.shopping.ui.cart.stateholder.CartStateHolder
import woowacourse.shopping.ui.cart.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.state.ProductUiModel

class CartActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val stateHolder = CartStateHolder(getCartItems(intent))
        setContent {

            AndroidshoppingcartTheme {
                CartScreen(
                    cartItems = stateHolder.cartItems,
                    onCloseClick = { finish() },
                    onDelete = { id ->
                        stateHolder.deleteCartItem(id)
                        setResult(RESULT_OK, deletedListResult(stateHolder.totalCartItems))
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

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun getDeletedList(intent: Intent?): List<ProductUiModel>? =
            intent?.getParcelableArrayListExtra(EXTRA_DELETED_LIST, ProductUiModel::class.java)

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private fun getCartItems(intent: Intent): List<ProductUiModel> =
            intent.getParcelableArrayListExtra(EXTRA_CART_ITEMS, ProductUiModel::class.java)
                ?: emptyList()

        private fun deletedListResult(items: List<ProductUiModel>): Intent =
            Intent().putParcelableArrayListExtra(EXTRA_DELETED_LIST, ArrayList(items))
    }
}
