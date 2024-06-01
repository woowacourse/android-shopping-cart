package woowacourse.shopping.data.cartItem

import kotlin.concurrent.thread

class CartItemLocalDataSource(cartItemDatabase: CartItemDatabase) {
    private val cartItemDao = cartItemDatabase.cartItemDao()

    fun saveCartItem(cartItemEntity: CartItemEntity): Long? {
        var addedCartItemId: Long? = null
        thread {
            addedCartItemId = cartItemDao.saveCartItem(cartItemEntity)
        }.join()
        return addedCartItemId
    }

    fun findPagingCarItem(
        offset: Int,
        pagingSize: Int,
    ): List<CartItemEntity> {
        return cartItemDao.findPagingCartItem(offset, pagingSize)
    }

    fun deleteCartItemById(itemId: Long) {
        thread { cartItemDao.deleteCartItemById(itemId) }.join()
    }

    fun getItemCount(): Int {
        var count = 0
        thread { count = cartItemDao.getItemCount() }.join()
        return count
    }

    fun getAllCartItems(): List<CartItemEntity> {
        var cartItems: List<CartItemEntity> = emptyList()
        thread { cartItems = cartItemDao.findAll() }.join()
        return cartItems
    }

    fun getItemTotalQuantity(): Int {
        var count = 0
        thread { count = cartItemDao.getTotalQuantity() }.join()
        return count
    }

    fun findCartItemEntityByProductId(id: Long): CartItemEntity? {
        var result: CartItemEntity? = null
        thread { result = cartItemDao.getCartItemByProductId(id) }.join()
        return result
    }

    fun updateCartItem(updatedCartItemEntity: CartItemEntity) {
        thread { cartItemDao.updateCartItem(updatedCartItemEntity) }.join()
    }
}
