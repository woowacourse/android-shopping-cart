package woowacourse.shopping.ui.shopping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias UiPrice = Price

@Parcelize
class Price(
    val value: Int
) : Parcelable
