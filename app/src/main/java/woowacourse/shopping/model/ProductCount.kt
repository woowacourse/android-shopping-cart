package woowacourse.shopping.model

import android.os.Parcelable
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.parcelize.Parcelize

typealias UiProductCount = ProductCount

@Parcelize
data class ProductCount(val value: Int) : Parcelable {
    fun toText(): String =
        if (value > 99) "99" else value.toString()

    fun getVisibility(): Int =
        if (value == 0) GONE else VISIBLE
}
