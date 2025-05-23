package woowacourse.shopping.data.inventory

import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

interface InventoryRepository2 {
    fun getAll(): List<Product>

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
    ): Page<Product>

    fun insert(product: Product)
}
