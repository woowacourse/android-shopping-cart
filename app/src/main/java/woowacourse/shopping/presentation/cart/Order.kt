package woowacourse.shopping.presentation.cart

data class Order(
    val cartItemId: Long,
    val productName: String,
    val image: String,
    val quantity: Int,
    val price: Int,
)
