package woowacourse.shopping.ui.cart

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

        val stateHolder = CartStateHolder(
            intent.getParcelableArrayListExtra("extra_cart_items", ProductUiModel::class.java)
                ?: emptyList<ProductUiModel>(),
        )
        setContent {

            AndroidshoppingcartTheme {
                CartScreen(
                    cartItems = stateHolder.cartItems,
                    onCloseClick = { finish() },
                    onDelete = { id ->
                        stateHolder.deleteCartItem(id)
                        setResult(
                            RESULT_OK,
                            Intent()
                                .putParcelableArrayListExtra(
                                    "deleted_cart_list", ArrayList(stateHolder.totalCartItems),
                                ),
                        )
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
}
