package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecentProductModel(
    val id: Long,
    val product: ProductModel
) : Parcelable
