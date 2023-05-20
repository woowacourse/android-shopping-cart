package woowacourse.shopping.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiBasketProduct = BasketProduct

@Parcelize
data class BasketProduct(
    val id: Int,
    val count: UiCount,
    val product: UiProduct,
    val checked: Boolean
) : Parcelable
