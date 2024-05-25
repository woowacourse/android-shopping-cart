package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.model.local.CartProductDto

interface ShoppingCartDataSource {
    fun insertCartProduct(
        productId: Long,
        name: String,
        price: Int,
        quantity: Int,
        imageUrl: String,
    ): Result<Unit>

    fun findCartProduct(productId: Long): Result<CartProductDto>

    fun updateCartProduct(
        productId: Long,
        quantity: Int,
    ): Result<Unit>

    fun getCartProductsPaged(
        page: Int,
        pageSize: Int,
    ): Result<List<CartProductDto>>

    fun getAllCartProducts(): Result<List<CartProductDto>>

    fun getCartProductsTotal(): Result<Int>

    fun deleteCartProduct(productId: Long): Result<Unit>

    fun deleteAllCartProducts(): Result<Unit>
}
