package woowacourse.shopping.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingCartItem(
    val id: Long = 0,
    val product: Product,
    val quantity: Int,
) : Parcelable
