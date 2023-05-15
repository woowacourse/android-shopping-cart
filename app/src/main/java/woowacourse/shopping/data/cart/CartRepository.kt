package woowacourse.shopping.data.cart

interface CartRepository {
    fun addCartProductId(productId: Int)
    fun deleteCartProductId(productId: Int)
    fun getCartProductIds(): List<Int>
}
