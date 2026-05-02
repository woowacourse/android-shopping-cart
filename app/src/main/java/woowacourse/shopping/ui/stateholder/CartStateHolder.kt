package woowacourse.shopping.ui.stateholder

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.android.parcel.Parcelize
import woowacourse.shopping.domain.Cart
import java.util.UUID

@Parcelize
class CartStateHolder(
    val initCart: Cart,
): Parcelable {
    var cart by mutableStateOf(initCart)

    var currentPage by mutableIntStateOf(0)

    fun removeProduct(id: UUID) {
        cart = cart.removeProduct(id)
    }

    fun onPrevious() {
        if (currentPage > 0) currentPage--
    }

    fun onNext() {
        if (currentPage < cart.size() / 5) currentPage++
    }

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(): Boolean = currentPage < (cart.size() - 1) / 5
}
