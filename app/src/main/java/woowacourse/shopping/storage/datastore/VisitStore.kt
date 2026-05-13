package woowacourse.shopping.storage.datastore

import kotlinx.coroutines.flow.StateFlow

interface VisitStore {
    val recentVisitedProductIds: StateFlow<List<Long>>

    suspend fun visit(productId: Long)
}
