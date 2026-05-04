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
    private lateinit var cartStateHolder: CartStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val restoredPage = savedInstanceState?.getInt("CURRENT_PAGE") ?: 0
        cartStateHolder = CartStateHolder(initialPage = restoredPage)

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                CartScreen(
                    stateHolder = cartStateHolder,
                    onClose = {
                        finish()
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(it),
                )
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_PAGE", cartStateHolder.currentPage)
    }
}
