package woowacourse.shopping.domain

data class CartItem(
    val cartItemId: Long,
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
