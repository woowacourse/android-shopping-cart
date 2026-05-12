package woowacourse.shopping.data.remote.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mockwebserver3.MockWebServer

object ProductWebServer {
    private val serverScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var isRun = false
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    private var server: MockWebServer? = null

    val baseUrl: String get() = server?.url("/").toString()

    fun start() {
        if(!isRun){
            server = MockWebServer().apply {
                dispatcher = ProductWebServerDispatcher()
            }
            serverScope.launch {
                server?.start()
                _isReady.value = true
            }
            isRun = true
        }
    }

    fun stop() {
        if(isRun) {
            server?.close()
            isRun = false
            serverScope.cancel()
            server = null
            _isReady.value = false
        }
    }
}
