package woowacourse.shopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cart: Cart = intent.getParcelableExtra(IntentKeys.CART_KEY) ?: Cart()

        onBackPressedDispatcher.addCallback {
            saveAndCloseActivity(cart)
        }

        setContent {
            val stateHolder = remember { CartStateHolder(cart) }

            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                CartScreen(
                    cart = stateHolder.cart,
                    onClose = {
                        saveAndCloseActivity(stateHolder.cart)
                    },
                    onDelete = { id ->
                        stateHolder.removeProduct(id)
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    currentPage = stateHolder.currentPage,
                    onPrevious = { stateHolder.onPrevious() },
                    onNext = { stateHolder.onNext() },
                    previousEnable = stateHolder.checkPreviousAvailable(),
                    nextEnable = stateHolder.checkNextAvailable(),
                )
            }
        }
    }

    fun saveAndCloseActivity(cart: Cart) {
        intent.putExtra(IntentKeys.CART_KEY, cart)
        setResult(RESULT_OK, intent)
        finish()
    }
}
