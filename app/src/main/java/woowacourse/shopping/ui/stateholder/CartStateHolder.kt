package woowacourse.shopping.ui.stateholder

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.android.parcel.Parcelize

@Parcelize
class CartStateHolder: Parcelable {
    var currentPage by mutableIntStateOf(0)
        private set

    fun onPrevious() {
        if (currentPage > 0) {
            currentPage--
        }
    }

    fun onNext(count: Int) {
        if (currentPage < count / 5) {
            currentPage++
        }
    }

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(count: Int): Boolean = currentPage < (count - 1) / 5
}
