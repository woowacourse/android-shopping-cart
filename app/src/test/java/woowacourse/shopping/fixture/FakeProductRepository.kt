package woowacourse.shopping.fixture

import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

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

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        val pagedItems = fakeProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < fakeProducts.size
        return PagedResult(pagedItems, hasNext)
    }
}
