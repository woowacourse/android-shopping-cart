package woowacourse.shopping.data.source.remote.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import mockwebserver3.MockWebServer

object MockServer {
    private val server =
        MockWebServer().apply {
            dispatcher = MockDispatcher()
        }

    private var startJob: Deferred<String>? = null

    @Synchronized
    fun start(scope: CoroutineScope) {
        if (startJob != null) return
        startJob =
            scope.async(Dispatchers.IO) {
                server.start()
                server.url("/").toString()
            }
    }

    suspend fun baseUrl(): String = requireNotNull(startJob) { "서버가 시작되지 않았습니다." }.await()
}
