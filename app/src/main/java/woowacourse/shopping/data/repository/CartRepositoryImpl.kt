package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.model.toProduct
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : CartRepository {
    override fun getAll(onResult: (Result<List<CartItem>>) -> Unit) =
        runCatchingInThread(onResult) {
            cartDataSource.getAll().toCartItems()
        }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) =
        runCatchingInThread(onResult) {
            cartDataSource.getTotalQuantity()
        }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        val entities = cartDataSource.loadCartItems(offset, limit)
        val cartItems = entities.toCartItems()
        val hasMore = entities.hasMore()
        PageableItem(cartItems, hasMore)
    }

    override fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartItem>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.findCartItemByProductId(productId).toCartItem()
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.findCartItemByProductId(productId).quantity
    }

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.addCartItem(productId, increaseQuantity)
    }

    override fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.decreaseCartItemQuantity(productId)
    }

    override fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.deleteCartItem(productId)
    }

    private fun CartEntity.toCartItem(): CartItem {
        val product = productDataSource.findProductById(productId).toProduct()
        return CartItem(product, quantity)
    }

    private fun List<CartEntity>.toCartItems(): List<CartItem> {
        val productIds = this.map { it.productId }
        val productMap = productDataSource.findProductsByIds(productIds).associateBy { it.id }

        return this.mapNotNull { entity ->
            val product = productMap[entity.productId]?.toProduct() ?: return@mapNotNull null
            CartItem(product, entity.quantity)
        }
    }

    private fun List<CartEntity>.hasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDataSource.existsItemCreatedAfter(lastCreatedAt)
    }
}
