package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiPrice = Price

@Parcelize
data class Price(
    val value: Int,
) : Parcelable
