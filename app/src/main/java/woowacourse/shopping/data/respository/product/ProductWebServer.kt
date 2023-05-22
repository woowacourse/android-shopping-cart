package woowacourse.shopping.data.respository.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray

object ProductWebServer {
    private lateinit var mockWebServer: MockWebServer
    internal var PORT = 0

    private const val DATA_COUNT = 20
    private var startPosition = 0

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: return MockResponse().setResponseCode(404)
            return when {
                path.startsWith("/shopping/products/") -> {
                    val productId = path.substringAfterLast("/").toLong()
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(getProductData(productId))
                }
                path == "/shopping/products" -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(getProductDatas(DATA_COUNT).toString())
                }
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    fun startServer() {
        Thread {
            mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.url("/")
            mockWebServer.dispatcher = dispatcher
            PORT = mockWebServer.port
        }.start()
    }

    fun getProductDatas(dataCount: Int): List<String> {
        val subToIndex =
            if (dataList.size > startPosition + dataCount)
                startPosition + dataCount
            else
                dataList.size

        val result = dataList.subList(startPosition, subToIndex)
        startPosition = subToIndex

        return result
    }

    fun getProductData(dataId: Long): String {
        val jsonArray = JSONArray(dataList.toString())

        for (index in 0 until jsonArray.length()) {
            val json = jsonArray.getJSONObject(index) ?: continue
            val id = json.getLong("id")
            if (id != dataId) continue
            return json.toString()
        }

        return errorData
    }

    private val dataList = listOf(
        """
            {
                "id": 0,
                "name": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                "price": 24900,
                "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 1,
                "name": "[리터스포트] 큐브 초콜릿 3종",
                "price": 7980,
                "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 2,
                "name": "[외할머니댁] 한우사골 칼국수 2인분",
                "price": 7900,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 3,
                "name": "[순창성가정식품] 마늘쫑 장아찌",
                "price": 3800,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 4,
                "name": "[하이포크] 한돈 급냉 삼겹살 500g",
                "price": 9900,
                "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 5,
                "name": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
                "price": 4900,
                "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 6,
                "name": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
                "price": 26000,
                "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 7,
                "name": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
                "price": 20800,
                "imageUrl": "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 8,
                "name": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                "price": 24900,
                "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 9,
                "name": "[리터스포트] 큐브 초콜릿 3종",
                "price": 7980,
                "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 10,
                "name": "[외할머니댁] 한우사골 칼국수 2인분",
                "price": 7900,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 11,
                "name": "[순창성가정식품] 마늘쫑 장아찌",
                "price": 3800,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 12,
                "name": "[하이포크] 한돈 급냉 삼겹살 500g",
                "price": 9900,
                "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 13,
                "name": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
                "price": 4900,
                "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 14,
                "name": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
                "price": 26000,
                "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 15,
                "name": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
                "price": 20800,
                "imageUrl": "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 16,
                "name": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                "price": 24900,
                "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 17,
                "name": "[리터스포트] 큐브 초콜릿 3종",
                "price": 7980,
                "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 18,
                "name": "[외할머니댁] 한우사골 칼국수 2인분",
                "price": 7900,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 19,
                "name": "[순창성가정식품] 마늘쫑 장아찌",
                "price": 3800,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 20,
                "name": "[하이포크] 한돈 급냉 삼겹살 500g",
                "price": 9900,
                "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 21,
                "name": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
                "price": 4900,
                "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 22,
                "name": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
                "price": 26000,
                "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 23,
                "name": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
                "price": 20800,
                "imageUrl": "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 24,
                "name": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
                "price": 24900,
                "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 25,
                "name": "[리터스포트] 큐브 초콜릿 3종",
                "price": 7980,
                "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 26,
                "name": "[외할머니댁] 한우사골 칼국수 2인분",
                "price": 7900,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 27,
                "name": "[순창성가정식품] 마늘쫑 장아찌",
                "price": 3800,
                "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 28,
                "name": "[하이포크] 한돈 급냉 삼겹살 500g",
                "price": 9900,
                "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 29,
                "name": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
                "price": 4900,
                "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 30,
                "name": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
                "price": 26000,
                "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
            }
        """.trimIndent(),
        """
            {
                "id": 31,
                "name": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
                "price": 20800,
                "imageUrl": "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
            }
        """.trimIndent(),
    )

    private val errorData = """
            {
                "id": -1,
                "name": "",
                "price": 0,
                "imageUrl": ""
            }
    """.trimIndent()
}
