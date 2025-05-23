package woowacourse.shopping.fixture

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class FakeProductRepository : ProductRepository {
    private val fakeProducts =
        List(100) { index ->
            Product(
                id = index.toLong(),
                imageUrl = "",
                name = "Product $index",
                price = (index + 1) * 1000,
            )
        }

    override fun getAll(): List<Product> = fakeProducts

    override fun getProductById(id: Long): Product? = fakeProducts.find { it.id == id }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        val pagedItems = fakeProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < fakeProducts.size
        return PagedResult(pagedItems, hasNext)
    }
}
