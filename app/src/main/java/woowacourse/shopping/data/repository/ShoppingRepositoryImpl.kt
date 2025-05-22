package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource,
) : ShoppingRepository {
    override fun getAll(onResult: (Result<List<CartItem>>) -> Unit) =
        runCatchingInThread(onResult) {
            cartDataSource.getAll().map { it.toCartItem() }
        }

    override fun getCartItemCount(onResult: (Result<Int>) -> Unit) =
        runCatchingInThread(onResult) {
            cartDataSource.getCartItemCount()
        }

    override fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        val products = productDataSource.loadProducts(offset, limit)
        val hasMore = productDataSource.calculateHasMore(offset, limit)
        val cartItems = products.toCartItems()
        PageableItem(cartItems, hasMore)
    }

    override fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartItem>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.findCartItemByProductId(productId).toCartItem()
    }

    override fun findProductInfoById(id: Long): Result<Product> = runCatching { productDataSource.findProductById(id) }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        val entities = cartDataSource.loadCartItems(offset, limit)
        val hasMore = entities.isHasMore()
        val cartItems = entities.map { it.toCartItem() }
        PageableItem(cartItems, hasMore)
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) = runCatchingInThread(onResult) {
        cartDataSource.findQuantityByProductId(productId)
    }

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) = runCatchingInThread(onResult) { cartDataSource.addCartItem(productId, increaseQuantity) }

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

    private fun CartEntity.toCartItem() = CartItem(findProductInfoById(productId).getOrThrow(), quantity)

    private fun List<Product>.toCartItems(): List<CartItem> =
        this.map {
            val quantity = cartDataSource.findQuantityByProductId(it.id)
            CartItem(it, quantity)
        }

    private fun List<CartEntity>.isHasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDataSource.existsItemCreatedAfter(lastCreatedAt)
    }
}
