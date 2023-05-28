package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProductModel(val id: Int, val product: ProductModel, val count: Int, val check: Boolean) :
    Parcelable
