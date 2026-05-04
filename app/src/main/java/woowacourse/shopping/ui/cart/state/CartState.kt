package woowacourse.shopping.ui.cart.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.shopping.state.ProductListState

class CartState {
    var currentPageIndex by mutableStateOf(0)
        private set

    fun increase() {
        currentPageIndex++
    }

    fun decrease() {
        currentPageIndex--
    }

    fun adjustCurrentPage(updatedLastPageIndex: Int) {
        if (currentPageIndex > updatedLastPageIndex) {
            currentPageIndex = updatedLastPageIndex
        }
    }

    companion object {
        val Saver = Saver<CartState, Int>(
            save = { it.currentPageIndex },
            restore = { CartState() }
        )
    }
}

@Composable
fun rememberCartState(): CartState =
    rememberSaveable(saver = CartState.Saver) {
        CartState()
    }