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

    // async function with callback

    fun loadAllProductsAsync(page: Int, callback: (List<Product>) -> Unit)

    fun loadProductsInCartAsync(page: Int, callback: (List<Product>) -> Unit)

    fun loadProductAsync(id: Long, callback: (Product) -> Unit)

    fun isFinalPageAsync(page: Int, callback: (Boolean) -> Unit)

    fun isCartFinalPageAsync(page: Int, callback: (Boolean) -> Unit)

    fun shoppingCartProductQuantityAsync(callback: (Int) -> Unit)

    fun increaseShoppingCartProductAsync(id: Long, callback: (Boolean) -> Unit)

    fun decreaseShoppingCartProductAsync(id: Long, callback: (Boolean) -> Unit)

    fun addShoppingCartProductAsync(id: Long, callback: (Boolean) -> Unit)

    fun removeShoppingCartProductAsync(id: Long, callback: (Boolean) -> Unit)
}
