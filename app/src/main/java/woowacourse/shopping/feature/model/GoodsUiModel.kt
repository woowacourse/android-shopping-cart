package woowacourse.shopping.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoodsUiModel(
    val id: Long,
    val name: String = "",
    val price: Int = 0,
    val thumbnailUrl: String = "",
) : Parcelable
