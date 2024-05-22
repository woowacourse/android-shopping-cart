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
        val oldCart = carts.values.find { it.productWithQuantityId == cart.productWithQuantityId }
        if (oldCart == null) { // 장바구니에 없는 상품인 경우
            carts[id] = cart.copy(id = id)
            return id++
        }
        return oldCart.id
    }

    override fun minusQuantityByProductWithQuantityId(productWithQuantityId: Long) {
        val oldCart =
            carts.values.find { it.productWithQuantityId == productWithQuantityId } ?: return
        minusCartCount(oldCart.id)
    }

    override fun deleteAll() {
        carts.clear()
    }

    override fun delete(id: Long) {
        carts.remove(id)
    }

    override fun deleteByProductWithQuantityId(productWithQuantityId: Long) {
        carts.values.find { it.productWithQuantityId == productWithQuantityId }?.let {
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

    private fun minusCartCount(cartId: Long) {
        carts[cartId]?.let {
            if (it.findProductWithQuantity().quantity.value == 1) delete(cartId)
            ProductWithQuantitiesImpl.minusCartCount(it.productWithQuantityId)
        }
    }

    fun Cart.findProductWithQuantity() = ProductWithQuantitiesImpl.find(this.productWithQuantityId)

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
