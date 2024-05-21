package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product
import kotlin.math.min

class FakeProductRepository(savedProducts: List<Product> = emptyList()) : ProductRepository {
    private val products: MutableMap<Long, Product> = savedProducts.associateBy { it.id }.toMutableMap()

    override fun find(id: Long): Product {
        return products[id] ?: throw IllegalArgumentException(INVALID_ID_MESSAGE)
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)
        return products.map { it.value }.subList(fromIndex, toIndex)
    }

    override fun totalCount(): Int = products.size

    companion object {
        private const val INVALID_ID_MESSAGE = "해당하는 id의 상품이 존재하지 않습니다."
    }
}
