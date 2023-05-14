package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiRecentProduct = RecentProduct

@Parcelize
data class RecentProduct(
    val id: Int,
    val product: Product,
) : Parcelable
