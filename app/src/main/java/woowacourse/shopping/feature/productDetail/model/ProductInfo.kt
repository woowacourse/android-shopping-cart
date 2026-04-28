package woowacourse.shopping.feature.productDetail.model

import androidx.compose.runtime.Stable

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
