package woowacourse.shopping.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun isOnline(): Flow<Boolean>
}
