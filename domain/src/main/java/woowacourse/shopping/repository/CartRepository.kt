package woowacourse.shopping.repository

interface CartRepository {
    fun addCartProductId(productId: Int)
    fun deleteCartProductId(productId: Int)
    fun getCartProductIds(limit: Int, offset: Int): List<Int>
}
