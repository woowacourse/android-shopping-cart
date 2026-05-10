package woowacourse.shopping.repository.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isNetworkConnected: StateFlow<Boolean>
}
