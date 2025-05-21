package woowacourse.shopping.fixture

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

class FakeCartProductRepository : CartProductRepository {
    private val items = mutableListOf<CartProduct>()
    private var nextId = 1L

    override fun getAll(): List<CartProduct> = items.toList()

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<CartProduct> {
        val pagedItems = items.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < items.size
        return PagedResult(pagedItems, hasNext)
    }

    override fun getQuantityByProductId(productId: Long): Int? = items.find { it.product.id == productId }?.quantity

    override fun updateQuantity(
        productId: Long,
        quantity: Int,
    ) {
        items.replaceAll {
            if (it.product.id == productId) it.copy(quantity = quantity) else it
        }
    }

    override fun deleteByProductId(productId: Long) {
        items.removeIf { it.product.id == productId }
    }

    override fun insert(productId: Long) {
        val product =
            Product(
                id = productId,
                imageUrl = "",
                name = "Product $productId",
                price = 1000,
            )
        items.add(
            CartProduct(
                id = nextId++,
                product = product,
            ),
        )
    }
}
