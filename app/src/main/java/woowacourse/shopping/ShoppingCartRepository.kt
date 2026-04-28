package woowacourse.shopping

interface ShoppingCartRepository {
    fun add(product: Product)

    fun getShoppingItems(): List<ShoppingCartItem>
}
