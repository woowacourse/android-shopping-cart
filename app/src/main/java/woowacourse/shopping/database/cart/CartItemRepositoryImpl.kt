package woowacourse.shopping.database.cart

import woowacourse.shopping.datasource.cart.CartItemDataSource
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository

class CartItemRepositoryImpl(
    private val cartItemDataSource: CartItemDataSource
) : CartItemRepository {
    override fun save(cartItem: CartItem) {
        cartItemDataSource.save(cartItem)
    }

    override fun findAllByIds(ids: List<Long>): List<CartItem> {
        return cartItemDataSource.findAllByIds(ids)
    }

    override fun findAllOrderByAddedTime(limit: Int, offset: Int): List<CartItem> {
        return cartItemDataSource.findAllOrderByAddedTime(limit, offset)
    }

    override fun findById(id: Long): CartItem? {
        return cartItemDataSource.findById(id)
    }

    override fun findByProductId(productId: Long): CartItem? {
        return cartItemDataSource.findByProductId(productId)
    }

    override fun countAll(): Int {
        return cartItemDataSource.countAll()
    }

    override fun existByProductId(productId: Long): Boolean {
        return cartItemDataSource.existByProductId(productId)
    }

    override fun updateCountById(id: Long, count: Int) {
        cartItemDataSource.updateCountById(id, count)
    }

    override fun deleteById(id: Long) {
        cartItemDataSource.deleteById(id)
    }
}