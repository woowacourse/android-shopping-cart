package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.screen.CartScreen
import woowacourse.shopping.ui.theme.AndroidShoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            AndroidShoppingTheme {
                CartScreen(
                    cartProducts = AppContainer.cartRepository,
                    onClose = { finish() },
                )
            }
        }
    }
}
