package woowacourse.shopping.feature.productDetail.model

import androidx.compose.runtime.Stable
import woowacourse.shopping.domain.model.product.Product

@Stable
data class ProductInfo(
    val productImageUrl: String,
    val productName: String,
    val formattedPrice: String,
    val formattedQuantity: String,
) {
    companion object {
        val PREVIEW =
            ProductInfo(
                productImageUrl = "",
                productName = "리자몽",
                formattedPrice = "10,000원",
                formattedQuantity = "1",
            )
    }
}

fun Product.toUiModel(): ProductInfo =
    ProductInfo(
        productImageUrl = imageUrl,
        productName = productTitle.value,
        formattedPrice = "%,d원".format(price.value),
        formattedQuantity = "0",
    )
