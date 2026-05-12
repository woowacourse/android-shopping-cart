package woowacourse.shopping.domain

class Cart(
    private val cartContents: List<CartContent>,
) {
    fun quantityOf(productId: String): Int {
        val cartItem = cartContents.firstOrNull { cartContent ->
            cartContent.hasProductId(productId)
        }
        return cartItem?.quantity
            ?: 0
    }

    fun cartContentsSizeOf(): Int = cartContents.size

    fun totalQuantityOf(): Int = cartContents.sumOf {
        it.quantity
    }

    fun getProductList(): List<Product> {
        return cartContents.map { it.product }
    }
}
