package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.entity.CartEntity

interface CartDataSource {
    fun getCartProductCount(): Result<Int>

    fun getTotalQuantity(): Result<Int?>

    fun getQuantityById(productId: Long): Result<Int>

    fun getPagedCartProducts(
        limit: Int,
        page: Int,
    ): Result<List<CartEntity>>

    fun existsByProductId(productId: Long): Result<Boolean>

    fun increaseQuantity(
        productId: Long,
        quantity: Int,
    ): Result<Unit>

    fun decreaseQuantity(productId: Long): Result<Unit>

    fun insertProduct(cartEntity: CartEntity): Result<Unit>

    fun deleteProductById(productId: Long): Result<Unit>
}
