package woowacourse.shopping.feature.products.model

import androidx.compose.runtime.Stable

@Stable
data class ShoppingProductInfo(
    val id: String,
    val productImageUrl: String,
    val productName: String,
    val price: String,
)
