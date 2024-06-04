package woowacourse.shopping.presentation.home.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductQuantity(
    val productId: Long,
    val quantity: Int,
) : Parcelable
