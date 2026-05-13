package woowacourse.shopping.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkStatusMonitor {
    val isConnected: StateFlow<Boolean>
}
