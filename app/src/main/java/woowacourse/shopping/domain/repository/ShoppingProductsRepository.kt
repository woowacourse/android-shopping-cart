package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingProductsRepository {
    fun loadAllProducts(page: Int): List<Product>

    fun loadProductsInCart(page: Int): List<Product>

    fun loadProduct(id: Int): Product

    fun isFinalPage(page: Int): Boolean

    fun isCartFinalPage(page: Int): Boolean

    fun shoppingCartProductQuantity(): Int

    fun increaseShoppingCartProduct(id: Int)

    fun decreaseShoppingCartProduct(id: Int)

    fun addShoppingCartProduct(id: Int)

    fun removeShoppingCartProduct(id: Int)
}
