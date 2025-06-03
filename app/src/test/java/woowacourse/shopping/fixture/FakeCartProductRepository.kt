package woowacourse.shopping.fixture

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartProductRepository

class FakeCartProductRepository : CartProductRepository {
    private val cartProducts = mutableListOf<CartProduct>()

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<CartProduct>>) -> Unit,
    ) {
        val pagedItems = cartProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < cartProducts.size
        val result = PagedResult(pagedItems, hasNext)
        onResult(Result.success(result))
    }

    override fun getQuantityByProductId(
        productId: Long,
        onResult: (Result<Int?>) -> Unit,
    ) {
        val result = cartProducts.find { it.product.id == productId }?.quantity
        onResult(Result.success(result))
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        val result = cartProducts.sumOf { it.quantity }
        onResult(Result.success(result))
    }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onResult: (Result<Unit>) -> Unit,
    ) {
        if (newQuantity == 0) {
            deleteByProductId(productId, onResult)
            return
        }
        if (currentQuantity == 0) {
            insert(productId, newQuantity)
            return
        }
        cartProducts.replaceAll {
            if (it.product.id == productId) it.copy(quantity = newQuantity) else it
        }
        onResult(Result.success(Unit))
    }

    override fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        cartProducts.removeIf { it.product.id == productId }
        onResult(Result.success(Unit))
    }

    private fun insert(
        productId: Long,
        quantity: Int,
    ) {
        val product =
            Product(
                id = productId,
                imageUrl = "",
                name = "Product $productId",
                price = 1000,
            )
        cartProducts.add(CartProduct(product, quantity))
    }
}
