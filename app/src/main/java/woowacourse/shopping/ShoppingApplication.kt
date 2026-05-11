package woowacourse.shopping

import android.app.Application
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.di.AppContainer
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startMockWebServer()
        AppContainer.init(this)
    }

    private fun startMockWebServer() {
        thread {
            val mockWebServer = MockWebServer()

            val products =
                """
[
  {
    "id": "a0000000-0000-0000-0000-000000000001",
    "name": "에센스 래쉬 프린세스 마스카라",
    "price": 9990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000002",
    "name": "거울 달린 아이섀도 팔레트",
    "price": 19990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/beauty/Eyeshadow%20Palette%20with%20Mirror/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000003",
    "name": "파우더 캐니스터",
    "price": 14990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/beauty/Powder%20Canister/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000004",
    "name": "레드 립스틱",
    "price": 12990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/beauty/Red%20Lipstick/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000005",
    "name": "레드 네일 폴리시",
    "price": 8990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/beauty/Red%20Nail%20Polish/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000006",
    "name": "캘빈클라인 CK 원",
    "price": 49990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/fragrances/Calvin%20Klein%20CK%20One/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000007",
    "name": "샤넬 코코 누아 오 드 퍼퓸",
    "price": 129990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/fragrances/Chanel%20Coco%20Noir%20Eau%20De/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000008",
    "name": "디올 자도르",
    "price": 89990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/fragrances/Dior%20J'adore/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000009",
    "name": "돌체 샤인 오 드 퍼퓸",
    "price": 69990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/fragrances/Dolce%20Shine%20Eau%20de/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000010",
    "name": "구찌 블룸 오 드 퍼퓸",
    "price": 79990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/fragrances/Gucci%20Bloom%20Eau%20de/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000011",
    "name": "안니발레 콜롬보 침대",
    "price": 1899000,
    "imageUrl": "https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Bed/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000012",
    "name": "안니발레 콜롬보 소파",
    "price": 2499000,
    "imageUrl": "https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Sofa/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000013",
    "name": "아프리칸 체리 협탁",
    "price": 299000,
    "imageUrl": "https://cdn.dummyjson.com/products/images/furniture/Bedside%20Table%20African%20Cherry/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000014",
    "name": "놀 사리넨 이그제큐티브 컨퍼런스 체어",
    "price": 499000,
    "imageUrl": "https://cdn.dummyjson.com/products/images/furniture/Knoll%20Saarinen%20Executive%20Conference%20Chair/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000015",
    "name": "거울 달린 원목 세면대",
    "price": 799000,
    "imageUrl": "https://cdn.dummyjson.com/products/images/furniture/Wooden%20Bathroom%20Sink%20With%20Mirror/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000016",
    "name": "사과",
    "price": 1990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Apple/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000017",
    "name": "소고기 스테이크",
    "price": 12990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Beef%20Steak/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000018",
    "name": "고양이 사료",
    "price": 8990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Cat%20Food/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000019",
    "name": "닭고기",
    "price": 9990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Chicken%20Meat/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000020",
    "name": "식용유",
    "price": 4990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Cooking%20Oil/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000021",
    "name": "오이",
    "price": 1490,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Cucumber/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000022",
    "name": "강아지 사료",
    "price": 10990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Dog%20Food/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000023",
    "name": "달걀",
    "price": 2990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Eggs/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000024",
    "name": "생선 스테이크",
    "price": 15990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Fish%20Steak/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000025",
    "name": "초록 피망",
    "price": 1990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Green%20Bell%20Pepper/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000026",
    "name": "청양고추",
    "price": 1490,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Green%20Chili%20Pepper/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000027",
    "name": "꿀 한 병",
    "price": 6990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Honey%20Jar/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000028",
    "name": "아이스크림",
    "price": 4990,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Ice%20Cream/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000029",
    "name": "주스",
    "price": 3490,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Juice/thumbnail.png"
  },
  {
    "id": "a0000000-0000-0000-0000-000000000030",
    "name": "키위",
    "price": 2490,
    "imageUrl": "https://cdn.dummyjson.com/products/images/groceries/Kiwi/thumbnail.png"
  }
]
                """.trimIndent()

            val dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse =
                        when (request.path) {
                            "/products" -> {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                                    .setBody(products)
                            }

                            else -> {
                                MockResponse().setResponseCode(404)
                            }
                        }
                }

            mockWebServer.dispatcher = dispatcher
            mockWebServer.start(12345)
            baseUrl = "http://localhost:12345/"
        }
    }

    companion object {
        var baseUrl: String = ""
    }
}
