package woowacourse.shopping.domain.product

import woowacourse.shopping.domain.cart.CartProduct

interface ProductOverViewRepository {
    fun findInRange(
        limit: Int,
        offset: Int,
        onResult: (Result<List<CartProduct>>) -> Unit,
    )

    fun findById(
        productId: Long,
        onResult: (Result<Product?>) -> Unit,
    )

    fun insertAll(
        vararg products: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun insertOrAddQuantity(
        productId: Long,
        delta: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    /**
     * @param productId 상품 ID
     * @param delta 증가, 감소 수량 (예: +1, -1)
     * @param onResult 작업 결과 콜백
     */
    fun updateQuantityByProductId(
        productId: Long,
        delta: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun removeInCart(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
