package woowacourse.shopping.data.server
//
//import com.example.domain.model.Product
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.mockwebserver.Dispatcher
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import okhttp3.mockwebserver.RecordedRequest
//
//class MockProductWebServer {
//    private val mockWebServer = MockWebServer()
//    private val baseUrl: String
//    private val products = """
//                [
//                    {
//                        "id": 1,
//                        "name": "치킨",
//                        "price": 10000,
//                        "imageUrl": "http://example.com/chicken.jpg"
//                    },
//                    {
//                        "id": 2,
//                        "name": "피자",
//                        "price": 20000,
//                        "imageUrl": "http://example.com/pizza.jpg"
//                    }
//                ]
//            """.trimIndent()
//
//    private val product = """
//                {
//                    "id": 1,
//                    "name": "치킨",
//                    "price": 10000,
//                    "imageUrl": "http://example.com/chicken.jpg"
//                }
//            """.trimIndent()
//
//    // 사용자의 요청에 따른 다른 응답을 주기 위해 디스패쳐 모드를 사용한다
//    private val dispatcher = object : Dispatcher() {
//        override fun dispatch(request: RecordedRequest): MockResponse {
//            return when (request.path) {
//                "/products" -> {
//                    MockResponse()
//                        .setHeader("Content-Type", "application/json")
//                        .setResponseCode(200)
//                        .setBody(products)
//                }
//                "/products/1" -> {
//                    MockResponse()
//                        .setHeader("Content-Type", "application/json")
//                        .setResponseCode(200)
//                        .setBody(product)
//                }
//                else -> {
//                    MockResponse().setResponseCode(404)
//                }
//            }
//        }
//    }
//
//    init {
//        mockWebServer.start(12345) // 포트 번호 우리가 지정
//
//        // 포트번호를 12345로 지정했으니 우리가 의도한 값이 나올 것이다.
//        baseUrl = String.format("http://localhost:%s", mockWebServer.port)
//        mockWebServer.dispatcher = dispatcher // 웹서버에게 디스패쳐 등록
//    }
//
//    fun requestFirstProducts(): List<Product> {
//        val okHttpClient = OkHttpClient()
//        val request = Request.Builder().
//
//
//    }
//
//}