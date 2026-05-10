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
import woowacourse.shopping.ui.component.screen.CartScreen
import woowacourse.shopping.ui.stateholder.CartStateHolder

class CartActivity : ComponentActivity() {
    private lateinit var cartStateHolder: CartStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val savedCart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getParcelable("extra_cart", Cart::class.java)
        } else {
            savedInstanceState?.getParcelable("extra_cart")
        }

        val intentCart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("extra_cart", Cart::class.java)
        } else {
            intent.getParcelableExtra("extra_cart")
        }

        val cart = savedCart ?: intentCart

        if (cart == null) {
            finish()
            return
        }

        val restoredPage = savedInstanceState?.getInt("CURRENT_PAGE") ?: 0
        cartStateHolder = CartStateHolder(initialCart = cart, initialPage = restoredPage)

        onBackPressedDispatcher.addCallback(this) {
            returnResultAndFinish()
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
                        returnResultAndFinish()
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

    private fun returnResultAndFinish() {
        val resultIntent = Intent().apply {
            putExtra("extra_cart", cartStateHolder.cart)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_PAGE", cartStateHolder.currentPage)
        outState.putParcelable("extra_cart", cartStateHolder.cart)
    }
}
