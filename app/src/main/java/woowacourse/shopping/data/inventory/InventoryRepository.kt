package woowacourse.shopping.data.inventory

import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

interface InventoryRepository {
    fun getOrNull(
        id: Int,
        onResult: (Product?) -> Unit,
    )

    fun getAll(onSuccess: (List<Product>) -> Unit)

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<Product>) -> Unit,
    )

    fun insert(product: Product)
}
