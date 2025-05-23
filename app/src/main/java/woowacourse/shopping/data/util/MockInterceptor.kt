package woowacourse.shopping.data.util

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import woowacourse.shopping.BuildConfig

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (!BuildConfig.IS_MOCK_MODE) {
            return chain.proceed(request)
        }
        val url = request.url
        val path = url.encodedPath

        return when {
            path == "/products/size" -> {
                createMockResponse(request, 200, getAllMockProductsSizeJson())
            }

            path.matches(Regex("/products/\\d+")) -> {
                val productId = path.substringAfterLast("/").toIntOrNull()
                if (productId != null) {
                    createMockResponse(request, 200, getProductByIdJson(productId))
                } else {
                    createMockResponse(request, 404, """{"error": "Invalid product ID"}""")
                }
            }

            path == "/products" && (url.queryParameter("limit") != null || url.queryParameter("offset") != null) -> {
                val limit = url.queryParameter("limit")?.toIntOrNull() ?: 20
                val offset = url.queryParameter("offset")?.toIntOrNull() ?: 0
                createMockResponse(request, 200, getPageMockProductsJson(limit, offset))
            }

            path == "/products" -> {
                createMockResponse(request, 200, getAllMockProductsJson())
            }

            else -> {
                createMockResponse(request, 404, """{"error": "Not Found"}""")
            }
        }
    }

    private fun createMockResponse(
        request: Request,
        responseCode: Int,
        jsonResponse: String,
    ): Response =
        Response
            .Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(responseCode)
            .message(if (responseCode == 200) "OK" else "Error")
            .body(jsonResponse.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("Content-Type", "application/json")
            .build()

    private fun getAllMockProductsSizeJson(): String =
        """
        {
            "size": ${allProducts.size}
            
        }
        """.trimIndent()

    private fun getProductByIdJson(productId: Int): String {
        val productIndex = productId - 1

        return if (productIndex in allProducts.indices) {
            """
        {
            "product": ${allProducts[productIndex]}
        }
        """
        } else {
            """{"error": "Product not found"}"""
        }
    }

    private fun getAllMockProductsJson(): String =
        """
        {
            "products": [
                ${allProducts.joinToString(",\n                ")}
            ]
        }
        """.trimIndent()

    private fun getPageMockProductsJson(
        limit: Int,
        offset: Int,
    ): String {
        val pagedProducts =
            allProducts.drop(offset).take(limit)

        return """
            {
                "products": [
                    ${pagedProducts.joinToString(",\n                ")}
                ]
            }
            """.trimIndent()
    }

    companion object {
        private val baseGoods =
            listOf(
                Triple("[굿즈-키홀더] 봇치 더 록! 애니판 테쿠토코 쵸이데카 아크릴키홀더B", 13500, "https://animate.godohosting.com/Goods/4522776264043.jpg"),
                Triple("[굿즈-스탠드팝] 진격의 거인 아크릴스탠드 리바이A", 18000, "https://animate.godohosting.com/Goods/4582682486458.jpg"),
                Triple("[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/렘", 10500, "https://animate.godohosting.com/Goods/4550621224034.jpg"),
                Triple("[굿즈-스탠드팝] Re: 제로부터 시작하는 이세계 생활 애니판 코롯토 아크릴피규어/람", 10500, "https://animate.godohosting.com/Goods/4550621224041.jpg"),
                Triple("[굿즈-클리어파일] SPY x FAMILY WIT스토어선행 클리어파일 샵 비주얼", 6000, "https://animate.godohosting.com/Goods/4549743820774.jpg"),
                Triple(
                    "[굿즈-마우스패드] BanG Dream! 뱅드림 Ani-Art 마우스패드 제5탄 Pastel＊Palettes",
                    27000,
                    "https://animate.godohosting.com/Goods/4571622733271.jpg",
                ),
                Triple(
                    "[굿즈-스탠드팝] VOCALOID 하츠네 미쿠 39Culture 2024 지방 아크릴 디오라마 후쿠오카",
                    18000,
                    "https://animate.godohosting.com/Goods/4582660578458.jpg",
                ),
            )

        private val allProducts =
            List(6) { baseGoods }
                .flatten()
                .mapIndexed { index, (name, price, imageUrl) ->
                    """
                    {
                        "id": ${index + 1},
                        "name": "${index + 1}) $name",
                        "price": $price,
                        "imageUrl": "$imageUrl"
                    }
                    """.trimIndent()
                }
    }
}
