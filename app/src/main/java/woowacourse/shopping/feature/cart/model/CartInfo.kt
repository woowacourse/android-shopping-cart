package woowacourse.shopping.feature.cart.model

import androidx.compose.runtime.Stable

@Stable
data class CartInfo(
    val id: String,
    val productImageUrl: String,
    val productName: String,
    val price: String,
) {
    companion object {
        val PREVIEW = CartInfo(
            id = "",
            productImageUrl = "",
            productName = "리자몽",
            price = "10,000원",
        )
    }
}
