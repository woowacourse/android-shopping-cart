package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

class CartItems(
    val values: List<CartItem> = emptyList(),
) {
    val totalQuantity: Int
        get() = values.sumOf { it.quantity.value }

    val totalPrice: Int
        get() = values.sumOf { it.totalPrice }

    fun addProduct(
        product: Product,
        quantity: Quantity = Quantity.ONE,
    ): CartItems {
        val existing = values.find { it.isSameProduct(product.id) }
        return if (existing == null) {
            CartItems(values + CartItem(product, quantity))
        } else {
            val updated = existing.copy(quantity = Quantity(existing.quantity.value + quantity.value))
            CartItems(values.map { if (it.isSameProduct(product.id)) updated else it })
        }
    }

    fun increase(productId: Int): CartItems {
        val target = findByProductId(productId) ?: return this
        return replace(target, target.increaseQuantity())
    }

    fun decrease(productId: Int): CartItems {
        val target = findByProductId(productId) ?: return this
        val decreased = target.decreaseQuantity()
        return if (decreased.quantity.isZero) {
            remove(productId)
        } else {
            replace(target, decreased)
        }
    }

    fun remove(productId: Int): CartItems = CartItems(values.filter { !it.isSameProduct(productId) })

    fun findQuantity(productId: Int): Quantity = findByProductId(productId)?.quantity ?: Quantity.ZERO

    fun contains(productId: Int): Boolean = findByProductId(productId) != null

    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<CartItem> {
        val safeFrom = fromIndex.coerceIn(0, values.size)
        val safeTo = toIndex.coerceIn(safeFrom, values.size)
        return values.subList(safeFrom, safeTo)
    }

    fun size(): Int = values.size

    private fun findByProductId(productId: Int): CartItem? = values.firstOrNull { it.isSameProduct(productId) }

    private fun replace(
        target: CartItem,
        replacement: CartItem,
    ): CartItems = CartItems(values.map { if (it.isSameCartItem(target)) replacement else it })
}
