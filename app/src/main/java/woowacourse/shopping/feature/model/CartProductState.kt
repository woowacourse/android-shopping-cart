package woowacourse.shopping.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProductState(
    val productId: Int,
    val productImageUrl: String,
    val productName: String,
    val productPrice: Int
) : Parcelable
