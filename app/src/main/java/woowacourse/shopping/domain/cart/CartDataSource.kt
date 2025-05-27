package woowacourse.shopping.domain.cart

interface CartDataSource {
    fun findInRange(
        limit: Int,
        offset: Int,
    ): Result<List<CartProduct>>

    fun findByProductId(productId: Long): Result<CartProduct?>

    /**
     *  @param productId 추가하려는 상품의 ID
     *  @param quantity 추가할 수량
     *  @return 성공 시 cartItemId 또는 -1 (이미 존재할 경우), 실패 시 예외를 담은 Result
     */
    fun insertByProductId(
        productId: Long,
        quantity: Int,
    ): Result<Long>

    fun insertOrAddQuantity(
        productId: Long,
        delta: Int,
    ): Result<Unit>

    /**
     * @param productId 추가하려는 상품 ID
     * @param delta 증가, 감소 수량 (예: +1, -1)
     * @return 성공 실패 여부를 담은 Result
     */
    fun updateQuantityByProductId(
        productId: Long,
        delta: Int,
    ): Result<Unit>

    fun deleteByProductId(productId: Long): Result<Unit>

    fun deleteByCartItemId(cartItemId: Long): Result<Unit>
}
