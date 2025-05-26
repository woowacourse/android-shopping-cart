package woowacourse.shopping.data.inventory

import woowacourse.shopping.domain.Page
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

interface InventoryRepository {
    fun getOrNull(
        id: Int,
        onResult: (ProductItem?) -> Unit,
    )

    fun getAll(onSuccess: (List<ProductItem>) -> Unit)

    fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<ProductItem>) -> Unit,
    )

    fun insert(product: ProductItem)

    fun insertAll(products: List<ProductItem>)

    fun clear()
}
