package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiProductCount = ProductCount

@Parcelize
data class ProductCount(val value: Int) : Parcelable {
    fun toText(): String = if (value > 99) "99" else value.toString()
}
