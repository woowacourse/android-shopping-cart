package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ids =
            intent
                .getStringExtra("id")
                ?.split(",")
                ?.filter { it.isNotBlank() } ?: emptyList()

        val cartStateHolder = CartStateHolder(ids)

        onBackPressedDispatcher.addCallback {
            saveAndCloseActivity(cartStateHolder.cart.cartProducts.items)
        }

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                CartScreen(
                    stateHolder = cartStateHolder,
                    onClose = {
                        saveAndCloseActivity(cartStateHolder.cart.cartProducts.items)
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(it),
                )
            }
        }
    }

    fun saveAndCloseActivity(items: List<Product>) {
        val itemsId = items.joinToString(",") { it.uuid.toString() }
        intent.putExtra("id", itemsId)
        setResult(RESULT_OK, intent)
        finish()
    }
}
