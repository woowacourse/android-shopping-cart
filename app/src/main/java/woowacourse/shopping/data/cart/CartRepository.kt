package woowacourse.shopping.data.cart

interface CartRepository {
    fun addCartProduct(productId: Int, count: Int)
    fun deleteCartProduct(productId: Int)
    fun subProductCount(productId: Int, count: Int)
    fun getCartEntities(): List<CartEntity>
    fun getCartEntity(productId: Int): CartEntity
}
