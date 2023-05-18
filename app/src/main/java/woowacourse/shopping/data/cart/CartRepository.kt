package woowacourse.shopping.data.cart

interface CartRepository {
    fun insertCartProduct(productId: Int, count: Int)
    fun updateCartProductCount(productId: Int, count: Int)
    fun deleteCartProduct(productId: Int)
    fun getCartEntities(): List<CartEntity>
    fun getCartEntity(productId: Int): CartEntity
}
