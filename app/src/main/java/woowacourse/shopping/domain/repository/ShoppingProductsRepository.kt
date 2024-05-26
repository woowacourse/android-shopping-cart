package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingProductsRepository {
    fun loadAllProducts(page: Int): List<Product>

    fun loadProductsInCart(page: Int): List<Product>

    fun loadProduct(id: Long): Product

    fun isFinalPage(page: Int): Boolean

    fun isCartFinalPage(page: Int): Boolean

    fun shoppingCartProductQuantity(): Int

    fun increaseShoppingCartProduct(id: Long)

    fun decreaseShoppingCartProduct(id: Long)

    fun addShoppingCartProduct(id: Long)

    fun removeShoppingCartProduct(id: Long)
}
