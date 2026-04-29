package woowacourse.shopping.feature.productDetail.model

import androidx.compose.runtime.Stable
import woowacourse.shopping.domain.model.product.Product

@Stable
data class ProductInfo(
    val productImageUrl: String,
    val productName: String,
    val price: String,
) {
    companion object {
        val PREVIEW = ProductInfo(
            productImageUrl = "",
            productName = "리자몽",
            price = "10,000원",
        )
    }
}

fun Product.toUiModel(): ProductInfo = ProductInfo(
    productImageUrl = imageUrl,
    productName = productTitle.value,
    price = "%,d원".format(price.value),
)
