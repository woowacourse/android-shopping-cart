package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository

class FakeShoppingRepository(
    private val initialProducts: List<Product>,
    private val initialCartItems: MutableMap<Long, Int> = mutableMapOf(),
) : ShoppingRepository {
    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): Result<PageableItem<Product>> {
        val products = initialProducts.drop(offset).take(limit)
        val hasMore = offset + limit < initialProducts.size
        return Result.success(PageableItem(products, hasMore))
    }

    override fun findProductInfoById(id: Long): Result<Product> {
        val product = initialProducts.find { it.id == id }
        return product?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException("Product with id $id not found"))
    }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        val items =
            initialCartItems.mapNotNull { (productId, quantity) ->
                val product = initialProducts.find { it.id == productId }
                product?.let { CartItem(it, quantity) }
            }
        val pagedItems = items.drop(offset).take(limit)
        val hasMore = offset + limit < initialCartItems.size

        onResult(Result.success(PageableItem(pagedItems, hasMore)))
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) {
        val quantity = initialCartItems.getOrDefault(productId, 0)
        onResult(Result.success(quantity))
    }

    override fun addCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        initialCartItems[productId] = (initialCartItems.getOrDefault(productId, 0)) + 1
        onResult(Result.success(Unit))
    }

    override fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val current = initialCartItems.getOrDefault(productId, 0)
        if (current > 1) {
            initialCartItems[productId] = current - 1
        } else {
            initialCartItems.remove(productId)
        }
        onResult(Result.success(Unit))
    }

    override fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        initialCartItems.remove(productId)
        onResult(Result.success(Unit))
    }
}
