package woowacourse.shopping.data.recent

import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.RecentProduct

interface RecentProductRepository {
    fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    )

    fun getLastProductBefore(
        product: InventoryItem.InventoryProduct,
        onResult: (RecentProduct?) -> Unit,
    )

    fun insert(recentProduct: RecentProduct)
}
