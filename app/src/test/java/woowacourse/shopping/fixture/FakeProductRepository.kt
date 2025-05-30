package woowacourse.shopping.fixture

import woowacourse.shopping.data.model.PagedResult
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

    override fun getProductById(
        id: Long,
        onResult: (Result<Product?>) -> Unit,
    ) {
        val result = fakeProducts.find { it.id == id }
        onResult(Result.success(result))
    }

    override fun getProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val result = ids.mapNotNull { id -> fakeProducts.find { it.id == id } }
        onResult(Result.success(result))
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<Product>>) -> Unit,
    ) {
        val pagedItems = fakeProducts.drop(offset).take(limit)
        val hasNext = offset + pagedItems.size < fakeProducts.size
        val result = PagedResult(pagedItems, hasNext)
        onResult(Result.success(result))
    }
}
