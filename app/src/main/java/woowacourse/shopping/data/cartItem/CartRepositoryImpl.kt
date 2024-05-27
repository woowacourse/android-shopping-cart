package woowacourse.shopping.data.cartItem

import android.content.Context
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class CartRepositoryImpl(context: Context) : CartRepository {
    private val cartItemDao = CartItemDatabase.getInstance(context).cartItemDao()

    private fun addCartItem(
        product: Product,
        quantity: Int,
    ): Long {
        var addedCartItemId = ERROR_SAVE_DATA_ID
        thread {
            addedCartItemId =
                cartItemDao.saveCartItem(CartItemEntity.makeCartItemEntity(product, quantity))
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

    override fun updateCartItem(updatedItem: CartItem) {
        val cartEntity = CartItemEntity.toCartItemEntity(updatedItem)
        thread { cartItemDao.updateCartItem(cartEntity) }.join()
    }

    override fun loadAllCartItems(): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        thread { cartItems = cartItemDao.findAll().map { it.toCartItem() } }.join()
        return cartItems
    }

    override fun getTotalNumberOfCartItems(): Int {
        var count = 0
        thread { count = cartItemDao.getTotalQuantity() }.join()
        return count
    }

    private fun findCartItemEntityByProductId(id: Long): CartItemEntity? {
        var result: CartItemEntity? = null
        thread { result = cartItemDao.getCartItemByProductId(id) }.join()
        return result
    }

    override fun updateIncrementQuantity(
        product: Product,
        incrementAmount: Int,
    ): Int {
        val existingCartItem = findCartItemEntityByProductId(product.id)
        return if (existingCartItem == null) {
            addCartItem(product, incrementAmount)
            incrementAmount
        } else {
            val updatedQuantity = existingCartItem.quantity + incrementAmount
            val updatedCartItem = existingCartItem.copy(quantity = updatedQuantity)
            thread { cartItemDao.updateCartItem(updatedCartItem) }.join()
            updatedQuantity
        }
    }

    override fun updateDecrementQuantity(
        product: Product,
        decrementAmount: Int,
        allowZero: Boolean,
    ): Int {
        val existingCartItem = findCartItemEntityByProductId(product.id) ?: throw NoSuchDataException()
        val updatedQuantity = existingCartItem.quantity - decrementAmount

        if (updatedQuantity == 0) {
            if (allowZero) {
                deleteCartItem(existingCartItem.id)
            }
        } else {
            val updatedCartItem = existingCartItem.copy(quantity = updatedQuantity)
            thread { cartItemDao.updateCartItem(updatedCartItem) }.join()
        }
        return updatedQuantity
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
