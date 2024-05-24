package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class CartRepositoryImpl(context: Context) : CartRepository {
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    override fun addCartItem(
        product: Product,
        quantity: Int,
    ): Long {
        var addedCartItemId = ERROR_SAVE_DATA_ID
        thread {
            addedCartItemId = cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product, quantity))
        }.join()
        if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        return addedCartItemId
    }

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        return cartItemDao.findPagingCartItem(offset, pagingSize).map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        thread { cartItemDao.deleteCartItemById(itemId) }.join()
    }

    override fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean {
        var totalItemCount = 0
        thread { totalItemCount = cartItemDao.getItemCount() }.join()
        val totalPageCount = (totalItemCount + itemsPerPage - 1) / itemsPerPage

        return currentPage < totalPageCount
    }

    override fun findCartItemWithProductId(productId: Long): CartItem? {
        var cartItem: CartItem? = null
        thread { cartItem = cartItemDao.getCartItemByProductId(productId)?.toCartItem() }.join()

        return cartItem
    }

    override fun updateCartItem(updatedItem: CartItem) {
        val cartEntity = CartItemEntity.toCartItemEntity(updatedItem)
        thread { cartItemDao.updateCartItem(cartEntity) }
    }

    override fun loadAllCartItems(): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        thread { cartItems = cartItemDao.findAll().map { it.toCartItem() } }.join()
        return cartItems
    }

    override fun findCartItemWithCartItemId(cartItemId: Long): CartItem? {
        var cartItem: CartItem? = null
        thread { cartItem = cartItemDao.findCartItemById(cartItemId)?.toCartItem() }.join()

        return cartItem
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
