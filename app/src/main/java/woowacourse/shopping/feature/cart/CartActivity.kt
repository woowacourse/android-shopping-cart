package woowacourse.shopping.feature.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.items
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.core.data.CartRepository
import woowacourse.shopping.feature.cart.ui.CartScreen
import woowacourse.shopping.ui.theme.AndroidshoppingTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidshoppingTheme {
                val cartItems = CartRepository.getCart().items
                CartScreen(
                    onDeleteItem = { CartRepository.deleteItem(it) },
                    cartItems = cartItems.toImmutableList(),
                )
            }
        }
    }
}
