package woowacourse.shopping.feature

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoodsUiModel(
    val name: String = "",
    val price: Int = 0,
    val thumbnailUrl: String = "",
) : Parcelable
