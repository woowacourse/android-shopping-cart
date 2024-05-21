package woowacourse.shopping.domain.model

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val cartItemCounter: CartItemCounter = CartItemCounter(),
) {
    companion object {
        val defaultProduct =
            Product(
                -1L,
                "",
                0,
                "",
                CartItemCounter(),
            )
    }
}
