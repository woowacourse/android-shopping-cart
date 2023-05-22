package woowacourse.shopping.data.product.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import woowacourse.shopping.data.product.ProductEntity

class ProductRemoteImpl : ProductRemote {

    private lateinit var mockWebServer: MockWebServer
    val products = """
        [
        {
            "id": 0,
            "name": "돌체 콜드 브루",
            "price": 10000,
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg"
        },
        {
            "id": 1,
            "name": "민트 콜드 브루",
            "price": 20000,
            "imageUrl": "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004312]_20221005145029134.jpg"
        },
        {
            "id" : 2,
            "name" : "에스프레소 마키아또",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[25]_20210415144211211.jpg",
            "price" : 5500
        },
        {
            "id" : 3,
            "name" : "카라멜 마키아또",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[126197]_20210415154609863.jpg",
            "price" : 6500
        },
        {
            "id" : 4,
            "name" : "라벤더 카페 브레베",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000004119]_20220412083025862.png",
            "price" : 5000
        },
        {
            "id" : 5,
            "name" : "아이스 더 그린 쑥 크림 라떼",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/02/[9200000004529]_20230206091908618.jpg",
            "price" : 4500
        },
        {
            "id" : 6,
            "name" : "아이스 화이트 초콜릿 모카",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110572]_20210415155545375.jpg",
            "price" : 5000
        },
        {
            "id" : 7,
            "name" : "클래식 민트 모카",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/10/[9200000004313]_20221005145156959.jpg",
            "price" : 5000
        },
        {
            "id" : 8,
            "name" : "바닐라 플랫 화이트",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002406]_20210415135507733.jpg",
            "price" : 6000
        },
        {
            "id" : 9,
            "name" : "바닐라 아포가토",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/09/[9200000004308]_20220916101121079.jpg",
            "price" : 5000
        },
        {
            "id" : 10,
            "name" : "리얼 블루베리 베이글",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003334]_20210310092057351.jpg",
            "price" : 5000
        },
        {
            "id" : 11,
            "name" : "리얼 치즈 베이글",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003335]_20210310092146175.jpg",
            "price" : 5000
        },
        {
            "id" : 12,
            "name" : "바질 토마토 크림치즈 베이글",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000003223]_20210315170846073.jpg",
            "price" : 5000
        },
        {
            "id" : 13,
            "name" : "미니 클래식 스콘",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[5110001099]_20210421161145644.jpg",
            "price" : 5000
        },
        {
            "id" : 14,
            "name" : "피넛 쑥 떡 스콘",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/03/[9300000004028]_20220314152820975.jpg",
            "price" : 5000
        },
        {
            "id" : 15,
            "name" : "거문 오름 크루아상",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000001361]_20210421133918737.jpg",
            "price" : 7000
        },
        {
            "id" : 16,
            "name" : "너티 크루아상",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9300000004372]_20230102083042772.jpg",
            "price" : 5000
        },
        {
            "id" : 17,
            "name" : "매콤 소시지 불고기 베이크",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002227]_20210421160744685.jpg",
            "price" : 5000
        },
        {
            "id" : 18,
            "name" : "미니 리프 파이",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/02/[9300000004008]_20220218143920309.jpg",
            "price" : 5000
        },
        {
            "id" : 19,
            "name" : "뺑 오 쇼콜라",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002431]_20210421164613125.jpg",
            "price" : 5000
        },
        {
            "id" : 20,
            "name" : "스모크드 소시지 브레드",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002445]_20210421172107585.jpg",
            "price" : 5000
        },
        {
            "id" : 21,
            "name" : "연유 밀크모닝",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9300000003175]_20210210174347318.jpg",
            "price" : 5000
        },
        {
            "id" : 22,
            "name" : "오름 치즈 케이츄리",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/07/[9300000003520]_20210727081330163.jpg",
            "price" : 5000
        },
        {
            "id" : 23,
            "name" : "올래 미니 크루아상",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2020/06/[9300000002848]_20200626143224628.jpg",
            "price" : 5000
        },
        {
            "id" : 24,
            "name" : "주상절리 파이",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002489]_20210421134243043.jpg",
            "price" : 5000
        },
        {
            "id" : 25,
            "name" : "크림치즈 브리오슈 보스톡",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9300000002931]_20210325161934333.jpg",
            "price" : 5000
        },
        {
            "id" : 26,
            "name" : "23 서머 그린 머그 355 ml",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[9300000004357]_20230428094403109.jpg",
            "price" : 15000
        },
        {
            "id" : 27,
            "name" : "바리스타춘식 캐릭터 머그",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/01/[9300000004332]_20230130132448085.jpg",
            "price" : 30000
        },
        {
            "id" : 28,
            "name" : "리저브 골드 테일 머그",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2021/08/[11123299]_20210804102144453.jpg",
            "price" : 25000
        },
        {
            "id" : 29,
            "name" : "스타벅스 브런치 세트",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2022/08/[9300000004214]_20220822132741879.jpg",
            "price" : 60000
        },
        {
            "id" : 30,
            "name" : "제주 한라봉 머그 237ml",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/03/[9300000004323]_20230307143135598.jpg",
            "price" : 40000
        },
        {
            "id" : 31,
            "name" : "베이스볼 머그",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/05/[9300000004471]_20230510163213366.jpg",
            "price" : 34000
        },
        {
            "id" : 32,
            "name" : "23 서머 기린 머그",
            "imageUrl" : "https://image.istarbucks.co.kr/upload/store/skuimg/2023/04/[11141873]_20230428125930267.jpg",
            "price" : 45000
        }
    ]
    """
        .trimIndent()

    init {
        initMockData()
    }

    private fun initMockData() {
        Thread {
            if (::mockWebServer.isInitialized) return@Thread
            mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.url("/")
            mockWebServer.dispatcher = makeProductMockDispatcher()
        }.start()
    }

    override fun getProductById(id: Int): ProductEntity {
        val products = getAllProducts()

        return products.find { it.id == id }
            ?: throw java.util.NoSuchElementException("해당 상품은 존재하지 않습니다")
    }

    override fun getProductInRange(from: Int, count: Int): List<ProductEntity> {
        val products = getAllProducts()
        val end = products.size.coerceAtMost(from + count)

        return products.subList(from, end)
    }

    private fun getAllProducts(): List<ProductEntity> {
        return buildList {
            val thread = Thread {
                val client = OkHttpClient()
                val host = "http://localhost:${mockWebServer.port}/"
                val path = "products"
                val request = Request.Builder().url(host + path).build()
                val response = client.newCall(request).execute()
                val body = response.body?.string() ?: return@Thread
                val json = JSONArray(body)

                (0 until json.length()).map {
                    add(json.getJSONObject(it).toProductEntity())
                }
            }
            thread.start()
            thread.join()
        }
    }

    private fun makeProductMockDispatcher(): Dispatcher = object : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            return when {
                path.contains("/products") -> {
                    MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(200).setBody(products)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun JSONObject.toProductEntity(): ProductEntity {

        return ProductEntity(
            id = this.getInt("id"),
            name = this.getString("name"),
            price = this.getInt("price"),
            imageUrl = this.getString("imageUrl")
        )
    }

    companion object {
        private const val PORT = 9876
    }
}
