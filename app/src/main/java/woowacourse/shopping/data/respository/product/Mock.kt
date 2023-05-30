package woowacourse.shopping.data.respository.product

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object Mock {
    val mockWebServer = MockWebServer()
    fun startMockWebServer() {
        mockWebServer.start(12345)
        val productsPage1 = """
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
        "price": 20800,
        imageUrl: "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
    },
    {
        "id": 8,
        "title": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
        "price": 24900,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 9,
        "title": "[리터스포트] 큐브 초콜릿 3종",
        "price": 7980,
        "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
    },
    {
        "id": 10,
        "title": "[외할머니댁] 한우사골 칼국수 2인분",
        "price": 7900,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
    },
    {
        "id": 11,
        "title": "[순창성가정식품] 마늘쫑 장아찌",
        "price": 3800,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
    },
    {
        "id": 12,
        "title": "[하이포크] 한돈 급냉 삼겹살 500g",
        "price": 9900,
        "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
    },
    {
        "id": 13,
        "title": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
        "price": 4900,
        "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
    },
    {
        "id": 14,
        "title": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
        "price": 26000,
        "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
    },
    {
        "id": 15,
        "title": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
        "price": 20800,
        imageUrl: "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
    },
    {
        "id": 16,
        "title": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
        "price": 24900,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 17,
        "title": "[리터스포트] 큐브 초콜릿 3종",
        "price": 7980,
        "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
    },
    {
        "id": 18,
        "title": "[외할머니댁] 한우사골 칼국수 2인분",
        "price": 7900,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
    },
    {
        "id": 19,
        "title": "[순창성가정식품] 마늘쫑 장아찌",
        "price": 3800,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
    }
    
                ]
        """.trimIndent()

        val productsPage2 = """
                [
                {
        "id": 20,
        "title": "[하이포크] 한돈 급냉 삼겹살 500g",
        "price": 9900,
        "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
    },
    {
        "id": 21,
        "title": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
        "price": 4900,
        "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
    },
    {
        "id": 22,
        "title": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
        "price": 26000,
        "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
    },
    {
        "id": 23,
        "title": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
        "price": 20800,
        imageUrl: "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
    },
    {
        "id": 24,
        "title": "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
        "price": 24900,
        "imageUrl": "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
    },
    {
        "id": 25,
        "title": "[리터스포트] 큐브 초콜릿 3종",
        "price": 7980,
        "imageUrl": "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
    },
    {
        "id": 26,
        "title": "[외할머니댁] 한우사골 칼국수 2인분",
        "price": 7900,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
    },
    {
        "id": 27,
        "title": "[순창성가정식품] 마늘쫑 장아찌",
        "price": 3800,
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
    },
    {
        "id": 28,
        "title": "[하이포크] 한돈 급냉 삼겹살 500g",
        "price": 9900,
        "imageUrl": "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
    },
    {
        "id": 29,
        "title": "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
        "price": 4900,
        "imageUrl": "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
    },
    {
        "id": 30,
        "title": "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
        "price": 26000,
        "imageUrl": "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
    },
    {
        "id": 31,
        "title": "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
        "price": 20800,
        imageUrl: "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
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

                    "/products?startPos=20&count=20" -> {
                        MockResponse().setHeader("Content-Type", "application/json")
                            .setResponseCode(200).setBody(productsPage2)
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
