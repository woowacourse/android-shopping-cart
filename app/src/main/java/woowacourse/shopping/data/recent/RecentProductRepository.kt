package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentItem

interface RecentProductRepository {
    fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentItem>) -> Unit,
    )

    fun insert(product: Product)

    fun clear()
}
