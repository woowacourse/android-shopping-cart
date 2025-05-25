package woowacourse.shopping.data.products

import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.model.product.Product

class ProductRepositoryImpl : ProductRepository {
    private val dummyProducts: List<Product> = dummyProductsData

    override fun getAll(): List<CartItem> = dummyProducts.map { CartItem(it) }

    override fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItem> {
        val end = (offset + limit).coerceAtMost(dummyProducts.size)
        return dummyProducts.map { CartItem(it) }.subList(offset, end)
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
        return
    }
}
