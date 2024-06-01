package woowacourse.shopping

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.utils.NoSuchDataException

class FakeCartRepository(inputs: List<CartItem> = emptyList()) : CartRepository {
    private val cartItems = inputs.toMutableList()

    override fun loadPagingCartItems(
        offset: Int,
        pagingSize: Int,
    ): List<CartItem> {
        val toIndex = (offset + pagingSize).coerceAtMost(cartItems.size)
        return if (offset in 0 until toIndex) {
            cartItems.subList(offset, toIndex)
        } else {
            emptyList()
        }
    }

    override fun deleteCartItem(itemId: Long) {
        cartItems.removeIf { it.id == itemId }
    }

    override fun hasNextCartItemPage(
        currentPage: Int,
        itemsPerPage: Int,
    ): Boolean {
        val totalPageCount = (cartItems.size + itemsPerPage - 1) / itemsPerPage
        return currentPage < totalPageCount
    }

    override fun loadAllCartItems(): List<CartItem> {
        return cartItems.toList()
    }

    override fun getTotalNumberOfCartItems(): Int {
        return cartItems.size
    }

    override fun updateIncrementQuantity(
        product: Product,
        incrementAmount: Int,
    ): Int {
        val index = cartItems.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            val cartItem = cartItems[index]
            val updatedQuantity = cartItem.quantity + incrementAmount
            cartItems[index] = cartItem.copy(quantity = updatedQuantity)
            return updatedQuantity
        } else {
            val newCartItem = CartItem(id = cartItems.size.toLong(), product = product, quantity = incrementAmount)
            cartItems.add(newCartItem)
            return incrementAmount
        }
    }

    override fun updateDecrementQuantity(
        product: Product,
        decrementAmount: Int,
        allowZero: Boolean,
    ): Int {
        val index = cartItems.indexOfFirst { it.product.id == product.id }
        if (index == -1) throw NoSuchDataException()

        val cartItem = cartItems[index]
        val updatedQuantity = cartItem.quantity - decrementAmount

        if (updatedQuantity == 0 && allowZero) {
            cartItems.removeAt(index)
        } else if (updatedQuantity > 0) {
            cartItems[index] = cartItem.copy(quantity = updatedQuantity)
        }
        return updatedQuantity
    }
}
