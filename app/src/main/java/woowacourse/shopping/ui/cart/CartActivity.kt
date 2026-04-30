package woowacourse.shopping.ui.cart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import woowacourse.shopping.ui.cart.ui.theme.AndroidshoppingcartTheme
import woowacourse.shopping.ui.state.ProductUiModel

class CartActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var cartItems = remember {
                mutableStateOf(
                    intent.getParcelableArrayListExtra("extra_cart_items", ProductUiModel::class.java)
                        ?: emptyList<ProductUiModel>(),
                )
            }

            AndroidshoppingcartTheme {
                CartScreen(
                    cartItems = cartItems.value,
                    onCloseClick = { finish() },
                    onDelete = { id ->
                        cartItems.value = cartItems.value.filter { it.id != id }
                        setResult(
                            RESULT_OK,
                            Intent()
                                .putParcelableArrayListExtra(
                                    "deleted_cart_list", ArrayList(cartItems.value),
                                ),
                        )
                    },
                )
            }
        }
    }
}
