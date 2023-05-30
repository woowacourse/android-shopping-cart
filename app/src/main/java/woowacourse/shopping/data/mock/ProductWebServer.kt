package woowacourse.shopping.data.mock

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object ProductWebServer {
    private lateinit var mockWebServer: MockWebServer
    internal var PORT = 12345

    fun startMockWebServer() {
        Thread {
            mockWebServer = MockWebServer()
            mockWebServer.start(PORT)
            mockWebServer.dispatcher = dispatcher
        }.start()
    }

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/products" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(products)
                }
                "/products/1" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(product)
                }
                "/cart-items" -> {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(cartItems)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private val product = """
                {
                    "id" : 1,
                    "name" : "헤어지자 말해요",
                    "price" : 100,
                    "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019"
                }
    """.trimIndent()

    private val products = """
        [
            {
                "id" : 1,
                "name" : "헤어지자 말해요",
                "price" : 100,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F494%2F9494325.jpg%3Ftype%3Dr204Fll%26v%3D20230419175019"
            },
            {
                "id" : 2,
                "name" : "사랑하면 안되는 사람",
                "price" : 2000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2Fe7daeb31%2F7369%2F420f%2F8b63%2Ffbc4434828c6%2F8358831.jpg%3Ftype%3Dr204Fll%26v%3D20221121174020"
            },
            {
                "id" : 3,
                "name" : "이별한 이유가 너무 아파",
                "price" : 3000,
                "imageUrl" : "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAyMjJfMTY3%2FMDAxNjc3MDU1MTQxMDQz.bJ5ycH3oziJcnNtFPIevmObndPJrFkbGpIBNYaYvdbkg.1VuwOhPa2Hl1VP9Q3h2HWLofZsg2KKhJPKX4SKE-7uIg.PNG.hanyy2525%2F20230222173042.png&type=a340"
            },
            {
                "id" : 4,
                "name" : "10,000 hours",
                "price" : 10000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F003%2F205%2F3205663.jpg%3Ftype%3Dr204Fll%26v%3D20220510140539"
            },
            {
                "id" : 5,
                "name" : "사랑하지 않아서 그랬어",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F009%2F399%2F9399613.jpg%3Ftype%3Dr204Fll%26v%3D20230406165433"
            },
            {
                "id" : 6,
                "name" : "Closer",
                "price" : 11000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F000%2F653%2F653057.jpg%3Ftype%3Dr204Fll%26v%3D20220813232859"
            },
            {
                "id" : 7,
                "name" : "off my face",
                "price" : 13000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F005%2F388%2F5388407.jpg%3Ftype%3Dr204Fll%26v%3D20220519115200"
            },
            {
                "id" : 8,
                "name" : "tiny riot",
                "price" : 15000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F005%2F465%2F5465856.jpg%3Ftype%3Dr204Fll%26v%3D20230214181541"
            },
            {
                "id" : 9,
                "name" : "Unstoppable",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F000%2F586%2F586403.jpg%3Ftype%3Dr204Fll%26v%3D20230423022551"
            },
            {
                "id" : 10,
                "name" : "You",
                "price" : 11000,
                "imageUrl" : "https://musicmeta-phinf.pstatic.net/album/007/275/7275980.jpg?type=r480Fll&v=20230331104058"
            },
            {
                "id" : 11,
                "name" : "운이 좋았지",
                "price" : 11000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F003%2F197%2F3197278.jpg%3Ftype%3Dr204Fll%26v%3D20220517215516"
            },
            {
                "id" : 12,
                "name" : "위로",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F003%2F137%2F3137611.jpg%3Ftype%3Dr204Fll%26v%3D20230105160009"
            },
            {
                "id" : 13,
                "name" : "A Book of Love",
                "price" : 9000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F004%2F586%2F4586617.jpg%3Ftype%3Dr204Fll%26v%3D20220518145013"
            },
            {
                "id" : 14,
                "name" : "중독된 사랑",
                "price" : 10000,
                "imageUrl" : "https://musicmeta-phinf.pstatic.net/album/007/353/7353472.jpg?type=r480Fll&v=20230331103301"
            },
            {
                "id" : 15,
                "name" : "스물다섯 스물하나",
                "price" : 6000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F000%2F398%2F398547.jpg%3Ftype%3Dr204Fll%26v%3D20230104015024"
            },
            {
                "id" : 15,
                "name" : "스물다섯 스물하나",
                "price" : 6000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F000%2F398%2F398547.jpg%3Ftype%3Dr204Fll%26v%3D20230104015024"
            },
            {
                "id" : 16,
                "name" : "사랑해도 될까요",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F953ee300%2Fee61%2F4526%2Fa92e%2Fbd189f5222db%2F8734372.jpg%3Ftype%3Dr204Fll%26v%3D20230113164435"
            },
            {
                "id" : 17,
                "name" : "사랑한만큼",
                "price" : 6000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F003%2F075%2F3075316.jpg%3Ftype%3Dr204Fll%26v%3D20220505101033"
            },
            {
                "id" : 18,
                "name" : "자격지심",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F008%2F104%2F8104134.jpg%3Ftype%3Dr204Fll%26v%3D20230316151015"
            },
            {
                "id" : 19,
                "name" : "B에게 쓰는 편지",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F007%2F994%2F7994936.jpg%3Ftype%3Dr204Fll%26v%3D20220907114853"
            },
            {
                "id" : 20,
                "name" : "고백연습",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F008%2F290%2F8290161.jpg%3Ftype%3Dr204Fll%26v%3D20221030175912"
            },
            {
                "id" : 21,
                "name" : "이제는 어떻게 사랑을 하나요",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F004%2F505%2F4505203.jpg%3Ftype%3Dr204Fll%26v%3D20230104100536"
            },
            {
                "id" : 22,
                "name" : "고백",
                "price" : 17000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F000%2F002%2F2976.jpg%3Ftype%3Dr204Fll%26v%3D20230104003029"
            },
            {
                "id" : 23,
                "name" : "딱 10Cm만",
                "price" : 10000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F008%2F095%2F8095694.jpg%3Ftype%3Dr204Fll%26v%3D20220925175913"
            },
            {
                "id" : 24,
                "name" : ""떠나보낼 준비해 둘 걸 그랬어"",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F008%2F238%2F8238693.jpg%3Ftype%3Dr204Fll%26v%3D20221018175907"
            },
            {
                "id" : 25,
                "name" : "스티커 사진",
                "price" : 12000,
                "imageUrl" : "https://search.pstatic.net/common?type=n&size=174x174&quality=95&direct=true&src=https%3A%2F%2Fmusicmeta-phinf.pstatic.net%2Falbum%2F007%2F701%2F7701874.jpg%3Ftype%3Dr204Fll%26v%3D20221208154058"
            },
        ]
    """.trimIndent()

    private val cartItems = """
                [
                    {
                        "id": 1,
                        "quantity": 5,
                        "product": {
                            "id": 1,
                            "price": 10000,
                            "name": "치킨",
                            "imageUrl": "http://example.com/chicken.jpg"
                        }
                    },
                    {
                        "id": 2,
                        "quantity": 1,
                        "product": {
                            "id": 2,
                            "price": 20000,
                            "name": "피자",
                            "imageUrl": "http://example.com/pizza.jpg"
                        }
                    }
                ]
    """.trimIndent()
}
