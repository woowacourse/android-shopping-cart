package woowacourse.shopping.domain.model

data class CartItem(val id: Long, val product: Product, val quantity: Int = 1) {
    val price: Int
        get() = product.price * quantity
}
