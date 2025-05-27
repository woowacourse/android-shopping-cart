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
        onSuccess: (PagedResult<CartProduct>) -> Unit,
    ) {
        val pagedItems = cartProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < cartProducts.size
        onSuccess(PagedResult(pagedItems, hasNext))
    }

    override fun getQuantityByProductId(
        productId: Long,
        onSuccess: (Int?) -> Unit,
    ) {
        onSuccess(cartProducts.find { it.product.id == productId }?.quantity)
    }

    override fun getTotalQuantity(onSuccess: (Int) -> Unit) {
        onSuccess(cartProducts.sumOf { it.quantity })
    }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
        onSuccess: () -> Unit,
    ) {
        if (newQuantity == 0) {
            deleteByProductId(productId) { onSuccess() }
            return
        }
        if (currentQuantity == 0) {
            insert(productId, newQuantity)
            return
        }
        cartProducts.replaceAll {
            if (it.product.id == productId) it.copy(quantity = newQuantity) else it
        }
        onSuccess()
    }

    override fun deleteByProductId(
        productId: Long,
        onSuccess: () -> Unit,
    ) {
        cartProducts.removeIf { it.product.id == productId }
        onSuccess()
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
