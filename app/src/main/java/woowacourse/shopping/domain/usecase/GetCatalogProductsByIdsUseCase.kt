package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class GetCatalogProductsByIdsUseCase(
    private val repository: ProductRepository,
) {
    operator fun invoke(
        productIds: List<Int>,
        callback: (List<CatalogProduct>) -> Unit,
    ) {
        thread {
            callback(repository.fetchCatalogProducts(productIds))
        }
    }
}
