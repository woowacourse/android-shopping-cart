package woowacourse.shopping.ui.stateholder

import android.app.Activity.RESULT_OK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.MockCatalog
import java.util.UUID

class CartStateHolder(
    ids: List<String>,
) {
    var items by mutableStateOf(ids.map { it ->
        val id = UUID.fromString(it.trim())
        MockCatalog.findProductById(id)
    })

    var currentPage by mutableStateOf(0)

    fun onPrevious() {
        if (currentPage > 0) currentPage--
    }

    fun onNext() {
        if (currentPage < items.size / 5) currentPage++
    }

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(): Boolean = currentPage < (items.size - 1) / 5
}
