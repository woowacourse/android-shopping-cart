package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

class CartStateHolder(
    initPage: Int,
) {
    var currentPage by mutableIntStateOf(initPage)
        private set

    fun onPrevious() {
        if (currentPage > 0) {
            currentPage--
        }
    }

    fun onNext(count: Int) {
        if (currentPage < (count - 1) / 5) {
            currentPage++
        }
    }

    fun isEmptyPage(
        count: Int,
        pageSize: Int,
    ) = count <= (currentPage * pageSize)

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(count: Int): Boolean = currentPage < (count - 1) / 5

    companion object {
        val Saver =
            Saver<CartStateHolder, Int>(
                save = { it.currentPage },
                restore = { saved ->
                    CartStateHolder(saved)
                },
            )
    }
}
