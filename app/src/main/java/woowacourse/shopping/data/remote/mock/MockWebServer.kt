package woowacourse.shopping.data.remote.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mockwebserver3.MockWebServer

object MockWebServer {
    private val server =
        MockWebServer().apply {
            dispatcher = MockServerDispatcher()
        }

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    val baseUrl: String get() = server.url("/").toString()

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            server.start()
            _isReady.value = true
        }
    }

    fun stop() {
        server.close()
    }
}
