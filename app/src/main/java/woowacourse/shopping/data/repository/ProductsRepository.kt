package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product

interface ProductsRepository {
    suspend fun findAll(pageRequest: PageRequest): Page<Product>

    suspend fun totalSize(): Int
}
