package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingRepository

class FakeShoppingRepository(
    private val initialProducts: List<Product>,
    private val initialCartItems: MutableMap<Long, Int> = mutableMapOf(),
) : ShoppingRepository {
    override fun getAll(onResult: (Result<List<CartItem>>) -> Unit) {
        val result =
            initialCartItems.mapNotNull { (productId, quantity) ->
                val product = initialProducts.find { it.id == productId }
                product?.let { CartItem(it, quantity) }
            }
        onResult(Result.success(result))
    }

    override fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        val products = initialProducts.drop(offset).take(limit)
        val cartItems = products.map { CartItem(it, initialCartItems.getOrDefault(it.id, 0)) }
        val hasMore = offset + limit < initialProducts.size
        onResult(Result.success(PageableItem(cartItems, hasMore)))
    }

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        initialCartItems[productId] = (initialCartItems.getOrDefault(productId, 0)) + 1
        onResult(Result.success(Unit))
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        val result = initialCartItems.values.sum()
        onResult(Result.success(result))
    }

    override fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartItem>) -> Unit,
    ) {
        val product = initialProducts.find { it.id == productId }
        val quantity = initialCartItems.getOrDefault(productId, 0)
        product?.let { onResult(Result.success(CartItem(it, quantity))) }
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
