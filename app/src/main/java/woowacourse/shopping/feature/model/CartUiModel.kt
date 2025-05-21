package woowacourse.shopping.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartUiModel(
    val id: Long,
    val name: String = "",
    val price: Int = 0,
    val thumbnailUrl: String = "",
    val quantity: Int = 0,
) : Parcelable
