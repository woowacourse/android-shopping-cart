package woowacourse.shopping.ui.shopping.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class ProductListState {
    var currentPageIndex by mutableStateOf(0)
        private set

    fun increase() {
        currentPageIndex++
    }

    fun decrease() {
        currentPageIndex--
    }

    companion object {
        val Saver =
            Saver<ProductListState, Int>(
                save = { it.currentPageIndex },
                restore = { ProductListState() },
            )
    }
}

@Composable
fun rememberProductListState(): ProductListState =
    rememberSaveable(saver = ProductListState.Saver) {
        ProductListState()
    }
