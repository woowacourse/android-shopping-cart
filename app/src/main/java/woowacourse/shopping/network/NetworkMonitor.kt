package woowacourse.shopping.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isNetworkConnected: StateFlow<Boolean>
}
