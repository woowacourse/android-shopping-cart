package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias UiPrice = Price

@Parcelize
data class Price(
    val value: Int,
) : Parcelable
