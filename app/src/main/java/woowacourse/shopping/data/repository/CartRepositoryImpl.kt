package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.CartDataSource
import woowacourse.shopping.data.source.local.cart.CartItemEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override suspend fun isLastPage(page: Int): Boolean = page * PAGE_SIZE >= cartDataSource.getTotalCount()

    override suspend fun addItem(
        productId: String,
        amount: Int,
    ) {
        val item = cartDataSource.getCartItemById(productId = productId)

        if (item == null) {
            cartDataSource.add(CartItemEntity(productId = productId, quantity = amount))

            return
        }

        cartDataSource.updateItem(cartItem = item.copy(quantity = item.quantity + amount))
    }

    override suspend fun deleteItem(productId: String) {
        cartDataSource.deleteItem(productId)
    }

    override suspend fun getCartItemByPage(page: Int): List<CartItem> {
        val cartItems = cartDataSource.getCartItems(offset = (page - 1) * PAGE_SIZE, count = PAGE_SIZE)

        return cartItems.map {
            it.toDomain()
        }
    }

    override suspend fun getCartItemCount(): Int = cartDataSource.getTotalCount()

    override suspend fun getItemCount(productId: String): Int =
        cartDataSource
            .getCartItemById(productId)
            ?.quantity
            ?: 0

    override suspend fun plusItemCount(product: Product) {
        val item = cartDataSource.getCartItemById(productId = product.id)

        if (item == null) {
            cartDataSource.add(CartItemEntity(productId = product.id, quantity = 1))

            return
        }

        cartDataSource.updateItem(cartItem = item.copy(quantity = item.quantity + 1))
    }

    override suspend fun minusItemCount(productId: String) {
        val item = cartDataSource.getCartItemById(productId = productId)

        requireNotNull(item) { "카트에 아이템이 존재하지 않습니다." }

        if (item.quantity == 1) {
            cartDataSource.deleteItem(productId = productId)
            return
        }

        cartDataSource.updateItem(cartItem = item.copy(quantity = item.quantity - 1))
    }

    fun CartItemEntity.toDomain(): CartItem =
        CartItem(
            productId = productId,
            quantity = quantity,
        )

    companion object {
        private const val PAGE_SIZE = 5
    }
}
