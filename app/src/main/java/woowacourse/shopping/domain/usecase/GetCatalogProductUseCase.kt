package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class GetCatalogProductUseCase(
    private val repository: ProductRepository,
) {
    operator fun invoke(
        lastId: Int,
        callback: (CatalogProduct?) -> Unit,
    ) {
        thread {
            val product = repository.fetchCatalogProduct(lastId)
            callback(product)
        }
    }
}
