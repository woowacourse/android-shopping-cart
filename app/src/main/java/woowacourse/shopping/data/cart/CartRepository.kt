package woowacourse.shopping.data.cart

interface CartRepository {
    fun addCartProduct(productId: Int)
    fun deleteCartProduct(productId: Int)
    fun subProductCount(productId: Int)
    fun getCartProducts(): List<CartEntity>
}
