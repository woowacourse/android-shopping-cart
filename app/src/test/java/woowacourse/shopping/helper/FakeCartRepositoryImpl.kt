package woowacourse.shopping.helper

import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import kotlin.math.min

class FakeCartRepositoryImpl : CartRepository {
    private val cartItems = mutableListOf<CartItem>()

    override fun save(
        product: Product,
        quantity: Int,
    ) {
        cartItems.add(
            CartItem(
                id = product.id,
                productId = product.id,
                productName = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                quantity = quantity,
            ),
        )
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
    }

    override fun cartItemSize(): Int {
        return cartItems.size
    }

    override fun productQuantity(productId: Long): Int {
        return cartItems.firstOrNull { it.id == productId }?.quantity ?: 0
    }

    override fun findOrNullByProductId(productId: Long): CartItem? {
        return cartItems.firstOrNull { it.productId == productId }
    }

    override fun find(cartItemId: Long): CartItem {
        return cartItems.first { it.id == cartItemId }
    }

    override fun findAll(): List<CartItem> {
        return cartItems
    }

    override fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val offset = page * pageSize
        val min = min(pageSize, cartItems.size)
        return cartItems.subList(offset, min)
    }

    override fun delete(cartItemId: Long) {
        cartItems.removeIf { it.id == cartItemId }
    }

    override fun deleteAll() {
        cartItems.clear()
    }
}
