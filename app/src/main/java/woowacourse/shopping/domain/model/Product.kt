package woowacourse.shopping.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int = INIT_QUANTITY_NUM,
    val imageUrl: String,
) : Parcelable {
    val totalPrice: Int
        get() = price * quantity

    companion object {
        const val INIT_QUANTITY_NUM = 0
    }
}
