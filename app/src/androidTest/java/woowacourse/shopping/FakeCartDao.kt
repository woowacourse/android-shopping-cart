package woowacourse.shopping

import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartDao
import kotlin.math.min

object FakeCartDao : CartDao {
    private const val OFFSET = 1
    private const val EXCEPTION_INVALID_ID = "Cart not found with id: %d"
    private var id: Long = 0
    private val carts = mutableMapOf<Long, Cart>()

    override fun itemSize() = carts.size

    override fun insert(cart: Cart): Long {
        val oldCart = carts.values.find { it.productId == cart.productId }
        if (oldCart == null) { // 장바구니에 없는 상품인 경우
            carts[id] = cart.copy(id = id)
            return id++
        }
        return oldCart.id
    }

    override fun deleteAll() {
        carts.clear()
    }

    override fun delete(id: Long) {
        carts.remove(id)
    }

    override fun deleteByProductId(productId: Long) {
        carts.values.find { it.productId == productId }?.let {
            delete(it.id)
        }
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

    override fun plusQuantityByProductId(productId: Long) {
        val oldProduct = carts.values.find { it.productId == productId }
        if (oldProduct == null) {
            insert(Cart(productId = productId).inc())
            return
        }
        carts[oldProduct.id] = oldProduct.inc()
    }

    override fun minusQuantityByProductId(productId: Long) {
        val oldCart = carts.values.find { it.productId == productId } ?: return
        if (oldCart.quantity.value == 1) {
            delete(oldCart.id)
            return
        }
        carts[oldCart.id] = oldCart.dec()
    }

    override fun updateQuantityIfProductExists(productId: Long): Int {
        TODO("Not yet implemented")
    }

    override fun findByProductId(productId: Long): Cart? {
        TODO("Not yet implemented")
    }

    override fun updateQuantityIfGreaterThanOne(productId: Long) {
        TODO("Not yet implemented")
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
