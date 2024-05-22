package woowacourse.shopping.model.data

import woowacourse.shopping.model.Cart
import kotlin.math.min

object CartsImpl : CartDao {
    private const val OFFSET = 1
    private const val EXCEPTION_INVALID_ID = "Cart not found with id: %d"
    private var id: Long = 0
    private val carts = mutableMapOf<Long, Cart>()

    override fun itemSize() = carts.size

    override fun save(cart: Cart): Long {
        val oldCart =
            carts.values.find { it.productWithQuantity.product.id == cart.productWithQuantity.product.id }
                ?.let {
                    carts[it.id]
                }

        if (oldCart == null) {
            carts[id] = cart.copy(id = id)
            return id++
        }
        val quantity = oldCart.productWithQuantity.quantity.plus(cart.productWithQuantity.quantity)
        carts.remove(oldCart.id)

        carts[oldCart.id] =
            oldCart.copy(productWithQuantity = oldCart.productWithQuantity.copy(quantity = quantity))

        return oldCart.id
    }

    override fun decreaseQuantity(cart: Cart) {
        val oldCart =
            carts.values.find { it.productWithQuantity.product.id == cart.productWithQuantity.product.id }
                ?.let {
                    carts[it.id]
                } ?: return

        val quantity = oldCart.productWithQuantity.quantity.plus(cart.productWithQuantity.quantity)
        if (quantity.value == 0) {
            delete(oldCart.id)
            return
        }
        carts[oldCart.id] =
            oldCart.copy(productWithQuantity = oldCart.productWithQuantity.copy(quantity = quantity))
    }

    override fun deleteAll() {
        carts.clear()
    }

    override fun delete(id: Long) {
        carts.remove(id)
    }

    override fun find(id: Long): Cart {
        return carts[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Cart> {
        return carts.map { it.value }
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart> {
        val fromIndex = (page - OFFSET) * pageSize
        val toIndex = min(fromIndex + pageSize, carts.size)
        return carts.values.toList().subList(fromIndex, toIndex)
    }

    override fun plusCartCount(cartId: Long) {
        carts[cartId]?.let {
            carts[cartId] = it.copy(productWithQuantity = it.productWithQuantity.inc())
        }
    }

    override fun minusCartCount(cartId: Long) {
        carts[cartId]?.let {
            if (it.productWithQuantity.quantity.value == 1) {
                delete(cartId)
                return
            }
            carts[cartId] = it.copy(productWithQuantity = it.productWithQuantity.dec())
        }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
