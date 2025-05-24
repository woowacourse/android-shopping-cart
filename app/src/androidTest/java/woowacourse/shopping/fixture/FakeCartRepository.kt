package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepository(
    initialCartItems: List<CartItem> = emptyList(),
) : CartRepository {
    private val cartItems = initialCartItems.toMutableList()

    override fun getAll(onResult: (Result<List<CartItem>>) -> Unit) {
        onResult(Result.success(cartItems.toList()))
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        val total = cartItems.sumOf { it.quantity }
        onResult(Result.success(total))
    }

    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        val pagedItems = cartItems.drop(offset).take(limit)
        val hasMore = (offset + limit) < cartItems.size
        onResult(Result.success(PageableItem(pagedItems, hasMore)))
    }

    override fun findCartItemByProductId(
        productId: Long,
        onResult: (Result<CartItem>) -> Unit,
    ) {
        val item = cartItems.find { it.product.id == productId }
        if (item != null) {
            onResult(Result.success(item))
        } else {
            onResult(Result.failure(NoSuchElementException("CartItem not found for productId: $productId")))
        }
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) {
        val quantity = cartItems.find { it.product.id == productId }?.quantity ?: 0
        onResult(Result.success(quantity))
    }

    override fun addCartItem(
        productId: Long,
        increaseQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val product = productsFixture.find { it.id == productId }
        if (product == null) {
            onResult(Result.failure(IllegalArgumentException("Product not found: $productId")))
            return
        }

        val existingItem = cartItems.find { it.product.id == productId }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + increaseQuantity)
            cartItems.remove(existingItem)
            cartItems.add(updatedItem)
        } else {
            cartItems.add(CartItem(product, increaseQuantity))
        }
        onResult(Result.success(Unit))
    }

    override fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val existingItem = cartItems.find { it.product.id == productId }
        if (existingItem != null) {
            val newQuantity = existingItem.quantity - 1
            cartItems.remove(existingItem)
            if (newQuantity > 0) {
                cartItems.add(existingItem.copy(quantity = newQuantity))
            }
        }
        onResult(Result.success(Unit))
    }

    override fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        val existingItem = cartItems.find { it.product.id == productId }
        if (existingItem != null) {
            cartItems.remove(existingItem)
        }
        onResult(Result.success(Unit))
    }
}
