package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.Products
import kotlin.uuid.ExperimentalUuidApi

interface ProductRepository {
    @OptIn(ExperimentalUuidApi::class)
    fun getAllProducts(): Products
}