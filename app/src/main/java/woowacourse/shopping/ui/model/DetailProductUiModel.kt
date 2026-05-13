package woowacourse.shopping.ui.model

import java.text.DecimalFormat

data class DetailProductUiModel(
    val id: String,
    val imageUrl: String,
    val title: String,
    val price: String,
    val quantity: Int,
) {
    companion object {
        fun of(
            name: String,
            price: Long,
            imageUrl: String,
            id: String,
            quantity: Int,
        ): DetailProductUiModel {
            val formattedPrice = "${DecimalFormat("#,###").format(price)}원"
            return DetailProductUiModel(
                title = name,
                price = formattedPrice,
                imageUrl = imageUrl,
                id = id,
                quantity = quantity,
            )
        }
    }
}
