package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class CartRepositoryImpl(
    private val productRepository: ProductRepository,
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartItems(
        limit: Int,
        offset: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        runCatchingInThread(
            block = {
                val products = cartDao.getCartItemPaged(limit, offset)
                PageableItem(products.loadProductDetails(), products.isHasMore())
            },
            callback = onResult,
        )
    }

    override fun deleteCartItem(
        id: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.delete(id).let { id } },
            callback = onResult,
        )
    }

    override fun addCartItem(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.insertOrUpdate(product.toProductEntity()) },
            callback = onResult,
        )
    }

    private fun List<CartEntity>.loadProductDetails(): List<CartItem> {
        val productIds = this.map { it.productId }
        val products = productRepository.findProductsByIds(productIds).getOrDefault(emptyList())
        return products.mapToCartItems(this)
    }

    private fun List<Product>.mapToCartItems(cartEntities: List<CartEntity>): List<CartItem> {
        val productMap = this.associateBy { it.id }
        return cartEntities.mapNotNull { cartEntity ->
            productMap[cartEntity.productId]?.let { product ->
                CartItem(cartEntity.cartId, product, cartEntity.quantity)
            }
        }
    }

    private fun List<CartEntity>.isHasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDao.existsItemCreatedAfter(lastCreatedAt)
    }
}
