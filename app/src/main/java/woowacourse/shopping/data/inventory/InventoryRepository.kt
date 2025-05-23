package woowacourse.shopping.data.inventory

import woowacourse.shopping.domain.Page
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

interface InventoryRepository {
    fun getOrNull(
        id: Int,
        onResult: (InventoryProduct?) -> Unit,
    )

    fun getAll(onSuccess: (List<InventoryProduct>) -> Unit)

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<InventoryProduct>) -> Unit,
    )

    fun insert(product: InventoryProduct)
}
