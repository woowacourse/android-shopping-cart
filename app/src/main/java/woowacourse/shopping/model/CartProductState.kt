package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProductState(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int,
    val count: Int,
    val checked: Boolean
) : Parcelable
