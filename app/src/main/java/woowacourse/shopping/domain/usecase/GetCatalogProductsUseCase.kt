package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CatalogProducts
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class GetCatalogProductsUseCase(
    private val repository: ProductRepository,
) {
    operator fun invoke(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit,
    ) {
        thread {
            val products = repository.fetchProducts(lastId, count)
            val lastProductId = products.lastOrNull()?.product?.id ?: lastId
            val hasMore = repository.hasMoreProducts(lastProductId)
            callback(CatalogProducts(products, hasMore))
        }
    }
}
