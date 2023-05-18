package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProductState(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    var count: Int,
    val checked: Boolean
) : Parcelable {

    companion object {
        const val MIN_COUNT_VALUE = 1
        const val MAX_COUNT_VALUE = 99
    }
}
