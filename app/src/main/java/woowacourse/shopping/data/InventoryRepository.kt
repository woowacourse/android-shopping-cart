package woowacourse.shopping.data

import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

interface InventoryRepository {
    fun getAll(): List<Product>

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
    ): Page<Product>
}
