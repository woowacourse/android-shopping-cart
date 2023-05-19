package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Int,
    val count: Int
) : Parcelable
