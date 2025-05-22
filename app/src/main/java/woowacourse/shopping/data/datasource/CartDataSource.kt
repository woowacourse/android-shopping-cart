package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity

interface CartDataSource {
    fun getCartProductCount(onResult: (Result<Int?>) -> Unit)

    fun getTotalQuantity(onResult: (Result<Int?>) -> Unit)

    fun getCartProducts(onResult: (Result<List<CartEntity>>) -> Unit)

    fun getCartItemById(
        productId: Long,
        onResult: (Result<CartEntity?>) -> Unit,
    )

    fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onResult: (Result<List<CartEntity>>) -> Unit,
    )

    fun existsByProductId(
        productId: Long,
        onResult: (Result<Boolean>) -> Unit,
    )

    fun increaseQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun decreaseQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun insertProduct(
        cartEntity: CartEntity,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProductById(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
