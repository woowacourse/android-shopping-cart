package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartResult
import woowacourse.shopping.domain.product.Product

interface CartRepository {
    fun insert(item: Product)

    fun delete(id: Long)

    fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): CartResult
}
