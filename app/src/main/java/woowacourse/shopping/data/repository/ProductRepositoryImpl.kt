package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.model.toProduct
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun findProductInfoById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    ) = runCatchingInThread(onResult) {
        productRemoteDataSource.findProductById(id).toProduct()
    }

    override fun loadProducts(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<Product>>) -> Unit,
    ) = runCatchingInThread(onResult) {
        productRemoteDataSource.loadProducts(offset, limit).let { pageableResponse ->
            PageableItem(
                items = pageableResponse.items.map { it.toProduct() },
                hasMore = pageableResponse.hasMore,
            )
        }
    }
}
