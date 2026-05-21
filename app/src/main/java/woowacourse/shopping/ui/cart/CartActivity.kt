package woowacourse.shopping.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.ui.cart.component.CartScreen

class CartActivity : ComponentActivity() {
    private lateinit var cartStateHolder: CartStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = (application as ShoppingApplication).cartRepository
        val restoredPage = savedInstanceState?.getInt("CURRENT_PAGE") ?: 0
        cartStateHolder = CartStateHolder(
            cartRepository = repository,
            coroutineScope = lifecycleScope,
            initialPage = restoredPage
        )

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        setContent {
            Scaffold(modifier = Modifier.Companion.fillMaxSize()) {
                CartScreen(
                    onDelete = { uuid -> cartStateHolder.onDeleteProduct(uuid) },
                    onNext = { cartStateHolder.onNext() },
                    onPrevious = { cartStateHolder.onPrevious() },
                    onIncrease = { uuid -> cartStateHolder.onIncreaseProduct(uuid) },
                    onDecrease = { uuid -> cartStateHolder.onDecreaseProduct(uuid) },
                    previousEnable = cartStateHolder.hasPreviousPage(),
                    nextEnable = cartStateHolder.hasNextPage(),
                    currentPage = cartStateHolder.currentPage,
                    onClose = {
                        finish()
                    },
                    getPartedItem = { uuid -> cartStateHolder.getPartedItem(uuid) },
                    isPageable = { cartStateHolder.isPageable() },
                    modifier =
                        Modifier.Companion
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
