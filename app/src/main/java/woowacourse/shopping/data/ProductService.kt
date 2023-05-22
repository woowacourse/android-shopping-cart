package woowacourse.shopping.data

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.data.product.ProductEntity

class ProductService : ProductDataSource {
    private val firstProducts = """
        [
            {
                "id": 1,
                "name": "BMW i8",
                "price": 13000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190529_183%2Fauto_1559133035619Mrf6z_PNG%2F20190529212501_Y1nsyfUj.png"
            },
            {
                "id": 2,
                "name": "포르쉐 카이엔",
                "price": 7000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220712_52%2Fauto_1657592866600a8C4k_PNG%2F20220712112738_wVnAbAoZ.png"
            },
            {
                "id": 3,
                "name": "링컨 컨티넨탈",
                "price": 5000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20200826_169%2Fauto_1598432748871YY7ah_PNG%2F20200826180547_GOQeTNRd.png"
            },
            {
                "id": 4,
                "name": "포르쉐 타이칸 GTS",
                "price": 12000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220707_93%2Fauto_1657154252364qU58P_PNG%2F20220707093720_s39RRXnC.png"
            },
            {
                "id": 5,
                "name": "한국지엠 마티즈",
                "price": 400,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png"
            },
            {
                "id": 6,
                "name": "한국지엠 황금마티즈",
                "price": 66666,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191216_108%2Fauto_1576459952156eBbWJ_PNG%2F20191216103230_XgY333pW.png"
            },
            {
                "id": 7,
                "name": "한국지엠 티코",
                "price": 100,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=http%3A%2F%2Fimgauto.naver.com%2F20170620_178%2Fauto_14979401212556uG7r_PNG%2F13554_2000_.png"
            },
            {
                "id": 8,
                "name": "마세라티 기블리",
                "price": 6000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220412_222%2Fauto_1649744422391G7FUf_PNG%2F20220412152012_cgt0dpvC.png"
            },
            {
                "id": 9,
                "name": "아우디 A8",
                "price": 18000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20210205_101%2Fauto_1612503585863g2uEr_PNG%2F20210205143938_MvJUSYv0.png"
            },
            {
                "id": 10,
                "name": "메르세데스-벤츠 S클래스",
                "price": 10000,
                "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220921_70%2Fauto_1663748987116ga7sL_PNG%2F20220921172938_E9f31l3l.png"
            }
        ]
    """.trimIndent()
    private val secondProducts = """
            [
                {
                    "id": 11,
                    "name": "제네시스 G80",
                    "price": 4000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221025_113%2Fauto_1666672525475ahX6W_PNG%2F20221025133519_6s3HtiEf.png"
                },
                {
                    "id": 12,
                    "name": "기아 K7 하이브리드",
                    "price": 2000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20201012_240%2Fauto_1602467609317sf99P_PNG%2F20201012105319_IKCTvOew.png"
                },
                {
                    "id": 13,
                    "name": "현대 그랜저",
                    "price": 2000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20220511_190%2Fauto_16522337997520h52J_PNG%2F20220511104952_pcU0etAp.png"
                },
                {
                    "id": 14,
                    "name": "현대 에쿠스",
                    "price": 4000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20191128_114%2Fauto_1574920869337nOXTj_PNG%2F20191128150107_69V7pu20.png"
                },
                {
                    "id": 15,
                    "name": "폭스바겐 골프 GTI",
                    "price": 3000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221215_9%2Fauto_1671066673583UltC4_PNG%2F20221215101104_AeCKVute.png"
                },
                {
                    "id": 16,
                    "name": "롤스로이스 팬텀",
                    "price": 60000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221125_198%2Fauto_1669344146553I2Nki_PNG%2F20221125114217_eFl3hRkq.png"
                },
                {
                    "id": 17,
                    "name": "벤틀리 컨티넨탈 GT",
                    "price": 25000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230302_110%2Fauto_1677745790170anBcf_PNG%2F20230302172948_Zpcosg7Q.png"
                },
                {
                    "id": 18,
                    "name": "람보르기니 우루스 S",
                    "price": 20000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20221114_271%2Fauto_16683888057987pf7D_PNG%2F20221114101956_kLPNhlDY.png"
                },
                {
                    "id": 19,
                    "name": "페라리 F8 스파이더",
                    "price": 32000,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20190916_38%2Fauto_1568614360509yR9gN_PNG%2F20190916151225_8wUAit8g.png"
                },
                {
                    "id": 20,
                    "name": "테슬라 모델3",
                    "price": 4400,
                    "itemImage": "https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fimgauto-phinf.pstatic.net%2F20230109_137%2Fauto_1673228022631WVIcJ_PNG%2F20230109103328_X2IV3rEI.png"
                }
            ]
    """.trimIndent()
    private val pathMap = mapOf<String, String>(
        "$PRODUCT_PATH/10/0" to firstProducts,
        "$PRODUCT_PATH/10/10" to secondProducts,
    )

    init {
        otherThread { startMockWebServer() }
    }

    private fun otherThread(action: () -> Unit) {
        Thread {
            action()
        }.let {
            it.start()
            it.join()
        }
    }

    private fun getProductPath(unit: Int, lastId: Long) = "$PRODUCT_PATH/$unit/$lastId"

    private fun startMockWebServer() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(PORT)

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path
                return if (pathMap.containsKey(path)) {
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody(pathMap[path]!!)
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher
    }

    override fun getProductEntities(
        unit: Int,
        lastId: Long,
    ): List<ProductEntity> {
        val okHttpClient = OkHttpClient()
        val requestProduct = Request.Builder()
            .url(BASE_URL + getProductPath(unit, lastId))
            .build()

        var productEntities: List<ProductEntity> = emptyList()
        otherThread {
            val body = okHttpClient.newCall(requestProduct).execute().body?.string()
            productEntities = JSONArray(body).toProductEntities()
        }
        return productEntities
    }

    private fun JSONArray.toProductEntities(): List<ProductEntity> {
        val products = mutableListOf<ProductEntity>()
        for (i in 0 until this.length()) {
            val jsonObject = this.getJSONObject(i)
            products.add(
                ProductEntity(
                    id = jsonObject.getLong("id"),
                    name = jsonObject.getString("name"),
                    price = jsonObject.getInt("price"),
                    itemImage = jsonObject.getString("itemImage"),
                ),
            )
        }
        return products
    }

    override fun isLastProductEntity(id: Long): Boolean {
        if (id >= 20) return true
        return false
    }

    override fun getProductEntity(id: Long): ProductEntity? {
        TODO("Not yet implemented")
    }

    override fun addProductEntity(name: String, price: Int, itemImage: String): Long {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PRODUCT_PATH = "/products"
        private const val PORT = 4101
        private const val BASE_URL = "http://localhost:$PORT"
    }
}
