package woowacourse.shopping.data.cartItem

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.utils.NoSuchDataException

class CartRepositoryImpl(private val cartItemLocalDataSource: CartItemLocalDataSource) : CartRepository {
    private fun addCartItem(
        product: Product,
        quantity: Int,
    ): Long {
        val entity = CartItemEntity.makeCartItemEntity(product, quantity)
        val addedCartItemId = cartItemLocalDataSource.saveCartItem(entity) ?: ERROR_SAVE_DATA_ID

        if (addedCartItemId == ERROR_SAVE_DATA_ID) throw NoSuchDataException()
        return addedCartItemId
    }

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        val pagingData = cartItemLocalDataSource.findPagingCarItem(offset, pagingSize)
        return pagingData.map { it.toCartItem() }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItemLocalDataSource.deleteCartItemById(itemId)
    }

    override fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean {
        val totalItemCount = cartItemLocalDataSource.getItemCount()
        val totalPageCount = (totalItemCount + itemsPerPage - 1) / itemsPerPage

        return currentPage < totalPageCount
    }

    override fun loadAllCartItems(): List<CartItem> {
        val cartItems = cartItemLocalDataSource.getAllCartItems()
        return cartItems.map { it.toCartItem() }
    }

    override fun getTotalNumberOfCartItems(): Int {
        return cartItemLocalDataSource.getItemTotalQuantity()
    }

    private fun findCartItemEntityByProductId(id: Long): CartItemEntity? {
        return cartItemLocalDataSource.findCartItemEntityByProductId(id)
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

            cartItemLocalDataSource.updateCartItem(updatedCartItem)
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

        if (updatedQuantity == 0 && allowZero) {
            deleteCartItem(existingCartItem.id)
        } else if (updatedQuantity > 0) {
            val updatedCartItem = existingCartItem.copy(quantity = updatedQuantity)
            cartItemLocalDataSource.updateCartItem(updatedCartItem)
        }
        return updatedQuantity
    }

    companion object {
        const val ERROR_SAVE_DATA_ID = -1L
    }
}
