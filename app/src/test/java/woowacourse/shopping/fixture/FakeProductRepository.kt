package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class FakeProductRepository : ProductRepository {
    override fun findProductInfoById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    ) {
        val product = productsFixture.find { it.id == id }
        if (product != null) {
            onResult(Result.success(product))
        } else {
            onResult(Result.failure(NoSuchElementException("Product not found: $id")))
        }
    }

    override fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<Product>>) -> Unit,
    ) {
        val pagedItems = productsFixture.drop(offset).take(limit)
        val hasMore = (offset + limit) < productsFixture.size
        onResult(Result.success(PageableItem(pagedItems, hasMore)))
    }
}
