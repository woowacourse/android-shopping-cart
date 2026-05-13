package woowacourse.shopping.data.local.entity

data class CartProductRow(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int,
)
