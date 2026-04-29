package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import woowacourse.shopping.feature.cart.bridge.CartBridge
import woowacourse.shopping.feature.cart.ui.CartScreen
import woowacourse.shopping.feature.cart.ui.CartStateHolder
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val cartBridge = remember { CartBridge() }
            val state = remember { CartStateHolder() }

            AndroidshoppingTheme {
                CartScreen(
                    state = state,
                    onDeleteItem = { id ->
                        cartBridge.removeFromCart(id)
                        state.getCartItems()
                    },
                )
            }
        }
    }
}
