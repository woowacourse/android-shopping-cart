package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiProducts = Products

@Parcelize
class Products(
    private val items: List<UiProduct> = emptyList(),
) : Parcelable {
    fun getItems(): List<UiProduct> = items
}
