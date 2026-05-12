package woowacourse.shopping.data.network

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    fun observeNetwork(): Flow<Boolean>
}
