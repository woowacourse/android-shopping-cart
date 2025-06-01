package woowacourse.shopping.data

class ShoppingCartDataSource(
    private val cartDao: CartDao,
) {
    fun upsertCartItem(cartItem: ShoppingCartEntity) {
        cartDao.upsertCartItem(cartItem)
    }

    fun deleteCartItem(productId: Int) {
        cartDao.deleteCartItem(productId)
    }
}
