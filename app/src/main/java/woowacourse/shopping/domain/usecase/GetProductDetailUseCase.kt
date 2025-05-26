package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class GetProductDetailUseCase(
    private val repository: ProductRepository,
) {
    operator fun invoke(
        productId: Int,
        callback: (CatalogProduct?) -> Unit,
    ) {
        thread {
            callback(repository.fetchCatalogProduct(productId))
        }
    }
}
