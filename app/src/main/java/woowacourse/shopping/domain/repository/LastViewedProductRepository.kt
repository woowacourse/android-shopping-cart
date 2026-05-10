package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
interface LastViewedProductRepository {
    suspend fun getLastViewedProduct(): Product?

    suspend fun saveLastViewedProduct(product: Product)
}
