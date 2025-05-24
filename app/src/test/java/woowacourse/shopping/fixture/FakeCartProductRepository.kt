package woowacourse.shopping.fixture

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartProductRepository

class FakeCartProductRepository : CartProductRepository {
    private val cartProducts = mutableListOf<CartProduct>()
    private var nextId = 1L

    override fun getAll(): List<CartProduct> = cartProducts.toList()

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<CartProduct> {
        val pagedItems = cartProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < cartProducts.size
        return PagedResult(pagedItems, hasNext)
    }

    override fun getQuantityByProductId(productId: Long): Int? = cartProducts.find { it.product.id == productId }?.quantity

    override fun getTotalQuantity(): Int = cartProducts.sumOf { it.quantity }

    override fun updateQuantity(
        productId: Long,
        currentQuantity: Int,
        newQuantity: Int,
    ) {
        if (newQuantity == 0) {
            deleteByProductId(productId)
            return
        }
        if (currentQuantity == 0) {
            insert(productId, newQuantity)
            return
        }
        cartProducts.replaceAll {
            if (it.product.id == productId) it.copy(quantity = newQuantity) else it
        }
    }

    override fun deleteByProductId(productId: Long) {
        cartProducts.removeIf { it.product.id == productId }
    }

    override fun insert(
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
        cartProducts.add(
            CartProduct(
                id = nextId++,
                product = product,
                quantity = quantity,
            ),
        )
    }
}
