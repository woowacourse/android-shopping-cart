package woowacourse.shopping.ui.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias UIProduct = Product

@Parcelize
class Product(
    val name: String,
    val price: UiPrice,
    val imageUrl: String
) : Parcelable
