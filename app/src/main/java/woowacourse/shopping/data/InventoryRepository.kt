package woowacourse.shopping.data

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.page.Page

interface InventoryRepository {
    fun getAll(): List<Product>

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
    ): Page<Product>
}
