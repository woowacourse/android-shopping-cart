package woowacourse.shopping

import android.content.Intent
import android.os.Build
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
import woowacourse.shopping.repository.InMemoryCartRepository
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder

import androidx.lifecycle.lifecycleScope

class CartActivity : ComponentActivity() {
    private lateinit var cartStateHolder: CartStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val restoredPage = savedInstanceState?.getInt("CURRENT_PAGE") ?: 0
        cartStateHolder = CartStateHolder(
            cartRepository = InMemoryCartRepository,
            coroutineScope = lifecycleScope,
            initialPage = restoredPage
        )

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) {
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
