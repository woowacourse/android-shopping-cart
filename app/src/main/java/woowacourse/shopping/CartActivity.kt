package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.screen.cartScreen
import woowacourse.shopping.ui.theme.androidshoppingTheme
import kotlin.uuid.ExperimentalUuidApi

class CartActivity : ComponentActivity() {
    @OptIn(ExperimentalUuidApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        @OptIn(ExperimentalUuidApi::class)
        setContent {
            androidshoppingTheme {
                cartScreen(
                    cartProducts = CartRepository,
                    onClose = { finish() },
                )
            }
        }
    }
}
