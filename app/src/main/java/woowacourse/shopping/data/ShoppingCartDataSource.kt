package woowacourse.shopping.data

class ShoppingCartDataSource(
    private val cartDao: CartDao,
) {
    fun getAllCartItems(): List<ShoppingCartEntity> = cartDao.getAllCartItems()

    fun upsertCartItem(cartItem: ShoppingCartEntity) {
        cartDao.upsertCartItem(cartItem)
    }

    fun deleteCartItem(productId: Long) {
        cartDao.deleteCartItem(productId)
    }
}
