package woowacourse.shopping.data.respository.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object Mock {
    val mockWebServer = MockWebServer()
    fun startMockWebServer() {
        mockWebServer.start(12345)
        val productsPage2 = """
                [
    {
        "id": 0,
        "title": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
        "price": 24900,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 1,
        "title": "[리터스포트] 큐브 초콜릿 3종",
        "price": 7980,
        "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
    },
    {
        "id": 2,
        "title": "[외할머니댁] 한우사골 칼국수 2인분",
        "price": 7900,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
    },
    {
        "id": 3,
        "title": "[순창성가정식품] 마늘쫑 장아찌",
        "price": 3800,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
    },
    {
        "id": 4,
        "title": "[하이포크] 한돈 급냉 삼겹살 500g",
        "price": 9900,
        "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
    },
    {
        "id": 5,
        "title": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
        "price": 4900,
        "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
    },
    {
        "id": 6,
        "title": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
        "price": 26000,
        "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
    },
    {
        "id": 7,
        "title": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
        ""price": 16500,
        "imageUrl": "https://product-image.kurly.com/product/image/b9c5bfa3-eb95-4e49-9b1f-62f9c4982f1b.jpg"
    },
    {
        "id": 8,
        "title": "[홀스타일] 프리미엄 시그니처 로션",
        "price": 19500,
        "imageUrl": "https://product-image.kurly.com/product/image/ff9579c7-4cc0-4980-b1fd-5d65c572ce43.jpg"
    },
    {
        "id": 9,
        "title": "[미트포유] 피자리아 피자 패키지 3종",
        "price": 14800,
        "imageUrl": "https://product-image.kurly.com/product/image/f262a051-0ea2-4cb4-9d09-232d07f24f58.jpg"
    },
    {
            "id": 10,
            "title": "[라라유] 우리쌀로 만든 발아현미물과자",
            "price": 6900,
            "imageUrl": "https://product-image.kurly.com/product/image/2f5e642b-455d-4307-8a1c-66064692db1b.jpg"
        },
        {
            "id": 11,
            "title": "[빛고을] 자연산 굴비",
            "price": 12900,
            "imageUrl": "https://product-image.kurly.com/product/image/8407ed6d-f99a-47d2-9a6b-b663c2271f68.jpg"
        },
        {
            "id": 12,
            "title": "[미트포유] 돼지고기 사골",
            "price": 10900,
            "imageUrl": "https://product-image.kurly.com/product/image/3d4332be-9364-405d-993b-af2a258c39f1.jpg"
        },
        {
        "id": 13,
        "title": "[팜에이트] 하루한끼 산머루 초콜릿",
        "price": 8900,
        "imageUrl": "https://product-image.kurly.com/product/image/151f7bc4-3f8a-4e25-b45b-1fca9d9da9d8.jpg"
    },
    {
        "id": 14,
        "title": "[포시즌] 허니버터팝콘",
        "price": 3700,
        "imageUrl": "https://product-image.kurly.com/product/image/125b9967-913f-421f-b5da-2de4ae49f6b7.jpg"
    },
    {
        "id": 15,
        "title": "[탐앤탐스] 신메뉴 바닐라빈 라떼",
        "price": 4800,
        "imageUrl": "https://product-image.kurly.com/product/image/411c8aeb-f1b5-47db-98d4-8dbd3b935502.jpg"
    },
    {
        "id": 16,
        "title": "[프레즐크래커] 미니 갈릭 크래커",
        "price": 5500,
        "imageUrl": "https://product-image.kurly.com/product/image/61c736d9-317e-4c07-bf8d-54e073f1f4c2.jpg"
    },
    {
        "id": 17,
        "title": "[유기농농장] 매콤한 불고기 스테이크",
        "price": 13900,
        "imageUrl": "https://product-image.kurly.com/product/image/ee1dbb92-98b5-4bea-944d-08f0e3891c7c.jpg"
    },
    {
        "id": 18,
        "title": "[오뚜기] 참깨라면",
        "price": 2500,
        "imageUrl": "https://product-image.kurly.com/product/image/6e95a3d2-2872-446d-a6f3-d1e95dd88412.jpg"
    },
    {
        "id": 19,
        "title": "[아웃백] 리베라 아웃백 스테이크",
        "price": 23900,
        "imageUrl": "https://product-image.kurly.com/product/image/91c8f248-1965-4a42-8350-2a7168d0c9ad.jpg"
    }
    ]
        """.trimIndent()

        val productsPage1 = """
                [
                {
        "id": 20,
        "title": "[칠성] 콜라",
        "price": 1500,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 21,
        "title": "[더마헤라] 센시티브 로션",
        "price": 27000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 22,
        "title": "[삼성전자] 갤럭시 S21 울트라",
        "price": 1299000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 23,
        "title": "[LG] 울트라와이드 게이밍 모니터",
        "price": 799000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 24,
        "title": "[후지필름] 인스턴트 카메라",
        "price": 89000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 25,
        "title": "[후지필름] 인스턴트 카메라",
        "price": 89000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 26,
        "title": "[후지필름] 인스턴트 카메라",
        "price": 89000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 27,
        "title": "[후지필름] 인스턴트 카메라",
        "price": 89000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 28,
        "title": "[후지필름] 인스턴트 카메라",
        "price": 89000,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    }
    
                ]
        """.trimIndent()

        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/products?startPos=0&count=20" -> {
                        MockResponse().setHeader("Content-Type", "application/json")
                            .setResponseCode(200).setBody(productsPage1)
                    }

                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

        mockWebServer.dispatcher = dispatcher
    }
}
