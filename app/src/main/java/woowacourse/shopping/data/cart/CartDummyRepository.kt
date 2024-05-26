package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.CartItemEntity.Companion.toDomainModel
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.CartItem.Companion.toEntity
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import kotlin.math.min

class CartDummyRepository(private val dao: CartDao) : CartRepository {
    private val productRepository: ProductRepository = ProductDummyRepository

    override fun addProduct(productId: Long) {
        val oldCartItem = dao.getAll().find { it.product.id == productId }
        if (oldCartItem == null) {
            val product: Product = productRepository.find(productId)
            dao.insert(CartItem(product, Quantity()).toEntity())
            return
        }
        var quantity = oldCartItem.quantity
        dao.delete(oldCartItem)
        dao.insert(oldCartItem.copy(quantity = ++quantity))
    }

    override fun deleteProduct(productId: Long) {
        val oldCartItem = dao.getAll().find { it.product.id == productId }
        oldCartItem ?: throw IllegalArgumentException(CANNOT_DELETE_MESSAGE)
        dao.delete(oldCartItem)
        if (oldCartItem.quantity.isMin()) {
            return
        }
        var quantity = oldCartItem.quantity
        dao.insert(oldCartItem.copy(quantity = --quantity))
    }

    override fun addCartItem(
        productId: Long,
        count: Int,
    ) {
        val oldCartItem = dao.getAll().find { it.product.id == productId }
        if (oldCartItem == null) {
            val product: Product = productRepository.find(productId)
            dao.insert(CartItem(product, Quantity(count)).toEntity())
            return
        }
        dao.delete(oldCartItem)
        dao.insert(oldCartItem.copy(quantity = Quantity(count)))
    }

    override fun deleteCartItem(productId: Long) {
        val oldCartItem = dao.getAll().first { it.product.id == productId }
        dao.delete(oldCartItem)
    }

    override fun deleteAll() {
        dao.drop()
    }

    override fun findAll(): List<CartItem> = dao.getAll().map { it.toDomainModel() }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val cart = dao.getAll()
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size)
        return cart.map { it.toDomainModel() }.subList(fromIndex, toIndex)
    }

    override fun find(productId: Long) = dao.getAll().firstOrNull { it.product.id == productId }?.toDomainModel()

    override fun findQuantityOfCartItems(products: List<Product>): List<CartItemQuantity> {
        val quantities =
            products.map { product ->
                val cartItem = dao.getAll().find { it.product.id == product.id }
                if (cartItem == null) {
                    CartItemQuantity(product.id, Quantity(0))
                } else {
                    CartItemQuantity(product.id, cartItem.quantity)
                }
            }
        return quantities
    }

    override fun count(): Int = dao.getAll().size

    companion object {
        private const val CANNOT_DELETE_MESSAGE = "삭제할 수 없습니다."
    }
}
