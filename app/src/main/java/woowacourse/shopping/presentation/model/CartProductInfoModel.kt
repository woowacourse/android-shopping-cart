package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartProductInfoModel(
    val productModel: ProductModel,
    val count: Int,
    val isOrdered: Boolean = false,
) : Parcelable {
    companion object {
        fun defaultInfo(): CartProductInfoModel = CartProductInfoModel(
            productModel = ProductModel(
                id = 7187,
                imageUrl = "https://search.yahoo.com/search?p=comprehensam",
                name = "Rebecca Carver",
                price = 5986,
            ),
            count = 4792,
            isOrdered = false,
        )
    }
}
