package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

class CartItems(
    val value: List<CartItem> = emptyList(),
) {
    val totalQuantity: Int
        get() = value.sumOf { it.quantity.value }

    val totalPrice: Int
        get() = value.sumOf { it.totalPrice }

    fun addProduct(product: Product): CartItems {
        val existingCartItem = findByProductId(product.id)
        return if (existingCartItem == null) {
            CartItems(value + CartItem(product = product, quantity = Quantity.ONE))
        } else {
            replace(existingCartItem, existingCartItem.increaseQuantity())
        }
    }
    fun increase(productId: String): CartItems {
        val target = findByProductId(productId) ?: return this
        return replace(target, target.increaseQuantity())
    }

    fun decrease(productId: String): CartItems {
        val target = findByProductId(productId) ?: return this
        val decreased = target.decreaseQuantity()
        return if (decreased.quantity.isZero) {
            remove(productId)
        } else {
            replace(target, decreased)
        }
    }

    fun remove(productId: String): CartItems = CartItems(value.filter { !it.isSameProduct(productId) })

    fun findQuantity(productId: String): Quantity =
        findByProductId(productId)?.quantity ?: Quantity.ZERO

    fun contains(productId: String): Boolean = findByProductId(productId) != null

    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<CartItem> {
        val safeFrom = fromIndex.coerceIn(0, value.size)
        val safeTo = toIndex.coerceIn(safeFrom, value.size)
        return value.subList(safeFrom, safeTo)
    }

    fun size(): Int = value.size

    private fun findByProductId(productId:String): CartItem? =
        value.firstOrNull{ it.isSameProduct(productId)}

    private fun replace(target:CartItem, replacement:CartItem): CartItems =
        CartItems(value.map{if(it.isSameCartItem(target)) replacement else it})
}
