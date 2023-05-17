package woowacourse.shopping.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiBasketProduct = BasketProduct

@Parcelize
data class BasketProduct(
    val id: Int,
    val product: UiProduct
) : Parcelable
