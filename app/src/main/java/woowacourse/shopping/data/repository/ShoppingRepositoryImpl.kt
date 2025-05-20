package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository

class ShoppingRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val cartDataSource: CartDataSource,
) : ShoppingRepository {
    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): Result<PageableItem<Product>> = productDataSource.loadProducts(offset, limit)

    override fun findProductInfoById(id: Long): Result<Product> = productDataSource.findProductById(id)

    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        cartDataSource.loadCartItems(offset, limit) { result ->
            result
                .onFailure { onResult(Result.failure(it)) }
                .onSuccess { pageableItem ->
                    val (entities, hasMore) = pageableItem
                    val products = entities.map { it.toCartItem() }
                    onResult(Result.success(PageableItem(products, hasMore)))
                }
        }
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) {
        cartDataSource.findQuantityByProductId(productId, onResult)
    }

    override fun addCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartDataSource.addCartItem(productId, onResult)
    }

    override fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartDataSource.decreaseCartItemQuantity(productId, onResult)
    }

    override fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartDataSource.deleteCartItem(productId, onResult)
    }

    private fun CartEntity.toCartItem() = CartItem(findProductInfoById(productId).getOrThrow(), quantity)
}
