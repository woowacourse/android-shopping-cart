package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.product.ProductRepository
import kotlin.math.min

class FakeProductRepository : ProductRepository {
    private val products: List<Product> = List(100) { Product(it.toLong() + 1, "Product ${it + 1}", "", 1000 * (it + 1)) }

    override fun fetchSinglePage(page: Int): List<Product> {
        val fromIndex = page * 20
        val toIndex = min(fromIndex + 20, products.size)
        return products.subList(fromIndex, toIndex)
    }

    override fun fetchProduct(id: Long): Product {
        return products.first { it.id == id }
    }
}
