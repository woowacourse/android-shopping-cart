package woowacourse.shopping.model

class ShoppingItem(
    private val product: Product,
    private val quantity: Int = 0,
) {
    init {
        require(quantity >= 0) { "상품의 수량은 음수일 수 없습니다." }
    }

    fun getProduct(): Product = product

    fun getProductId(): Long = product.id

    fun getQuantity(): Int = quantity

    fun getProductQuantityPrice(quantity: Int = this.quantity): Int = product.getPrice() * quantity
}
