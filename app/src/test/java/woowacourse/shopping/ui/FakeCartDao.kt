package woowacourse.shopping.ui

import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartDao
import kotlin.math.min

object FakeCartDao : CartDao {
    private const val NOT_UPDATED_FLAG = -1
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
        val isUpdated = updateQuantityIfProductExists(productId)
        if (isUpdated == NOT_UPDATED_FLAG) {
            insert(Cart(productId = productId).inc())
            return
        }
    }

    override fun updateQuantityIfProductExists(productId: Long): Int {
        carts.values.find { it.productId == productId }?.let {
            carts[it.id] = it.inc()
            return it.id.toInt()
        }
        return NOT_UPDATED_FLAG
    }

    override fun minusQuantityByProductId(productId: Long) {
        findByProductId(productId)?.let {
            if (it.quantity.value > 1) {
                updateQuantityIfGreaterThanOne(productId)
                return
            }
            delete(it.id)
        }
    }

    override fun findByProductId(productId: Long): Cart? {
        return carts.values.find { it.productId == productId }
    }

    override fun updateQuantityIfGreaterThanOne(productId: Long) {
        findByProductId(productId)?.let {
            carts[it.id] = it.dec()
        }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
