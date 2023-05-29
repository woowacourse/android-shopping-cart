package woowacourse.shopping.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiCount = Count

@Parcelize
class Count(
    val value: Int
) : Parcelable
