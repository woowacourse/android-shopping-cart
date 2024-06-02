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

    fun loadAllProductsAsync(
        page: Int,
        callback: (List<Product>) -> Unit,
    )

    fun loadProductsInCartAsync(
        page: Int,
        callback: (List<Product>) -> Unit,
    )

    fun loadProductAsync(
        id: Long,
        callback: (Product) -> Unit,
    )

    fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    )

    fun isCartFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    )

    fun shoppingCartProductQuantityAsync(callback: (Int) -> Unit)

    fun increaseShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    )

    fun decreaseShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    )

    fun addShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    )

    fun removeShoppingCartProductAsync(
        id: Long,
        callback: (Boolean) -> Unit,
    )

    // return result
    // 위에 있는 함수 + Async + Reusult 라는 네이밍으로, 콜백의 파라미터는 Result 로 감싼 것들로 메서드 만들어줘
    fun loadAllProductsAsyncResult(
        page: Int,
        callback: (Result<List<Product>>) -> Unit,
    )

    fun loadProductsInCartAsyncResult(
        page: Int,
        callback: (Result<List<Product>>) -> Unit,
    )

    fun loadProductAsyncResult(
        id: Long,
        callback: (Result<Product>) -> Unit,
    )

    fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    )

    fun isCartFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    )

    fun shoppingCartProductQuantityAsyncResult(callback: (Result<Int>) -> Unit)

    fun increaseShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun decreaseShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun addShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Long>) -> Unit,
    )

    fun removeShoppingCartProductAsyncResult(
        id: Long,
        callback: (Result<Unit>) -> Unit,
    )

}
