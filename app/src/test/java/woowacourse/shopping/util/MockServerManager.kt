package woowacourse.shopping.util

// object MockServerManager {
//    private val server = MockWebServer()
//    private var serverStarted = false
//
//    fun setDispatcher(dispatcher: Dispatcher) {
//        server.dispatcher = dispatcher
//    }
//
//    fun start() {
//        if (!serverStarted) {
//            server.start()
//            serverStarted = true
//        }
//    }
//
//    fun shutdown() {
//        if (serverStarted) {
//            server.shutdown()
//            serverStarted = false
//        }
//    }
//
//    fun getBaseUrl(): HttpUrl = server.url("/")
//
//    fun jsonResponse(
//        code: Int,
//        body: String,
//    ): MockResponse =
//        MockResponse()
//            .setResponseCode(code)
//            .addHeader("Content-Type", "application/json")
//            .setBody(body)
// }
