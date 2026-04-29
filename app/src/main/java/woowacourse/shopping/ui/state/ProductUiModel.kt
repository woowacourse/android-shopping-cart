package woowacourse.shopping.ui.state

import android.icu.text.DecimalFormat

data class ProductUiModel(val name: String, val price: String, val imageUrl: String, val id: String) {
    companion object {
        fun of(
            name: String,
            price: Int,
            imageUrl: String,
            id: String,
        ): ProductUiModel {
            val formattedPrice = "${
                DecimalFormat("#,###").format(price)
            }원"
            return ProductUiModel(
                name = name,
                price = formattedPrice,
                imageUrl = imageUrl,
                id = id,
            )
        }
    }
}
