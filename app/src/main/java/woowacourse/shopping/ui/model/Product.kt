package woowacourse.shopping.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UiProduct = Product

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: UiPrice,
    val imageUrl: String,
    var basketCount: Int = 0
) : Parcelable
