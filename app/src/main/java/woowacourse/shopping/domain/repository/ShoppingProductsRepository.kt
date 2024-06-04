package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingProductsRepository {
    fun loadAllProductsAsyncResult(
        page: Int,
        callback: (Result<List<Product>>) -> Unit,
    )

    fun loadAllProductsAsyncResult2(
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

    fun putItemInCartAsyncResult(
        id: Long,
        quantity: Int,
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
