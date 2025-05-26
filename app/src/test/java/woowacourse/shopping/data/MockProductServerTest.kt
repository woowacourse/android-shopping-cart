package woowacourse.shopping.data

import okhttp3.OkHttpClient
import okhttp3.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.product.remote.MockProductServer

class MockProductServerTest {
    private val mockProductServer = MockProductServer()

    @Test
    fun `마지막 상품 id 와 가져올 개수를 통해 상품 목록을 불러온다`() {
        // given:
        mockProductServer.start(1234)
        val client = OkHttpClient()

        val request =
            Request
                .Builder()
                .url("http://localhost:1234/products?lastProductId=&size=3")
                .build()

        // when:
        val response = client.newCall(request).execute()

        // then:
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.string()).isEqualTo(
            """
            [
            {
              "id": 1,
              "name": "럭키",
              "price": 4000,
              "imageUrl": "https://i.namu.wiki/i/ExNTyOB5363wFnhGLSfRPOSj9G5VwSQiISkjICuIVI-8S8djFN8cJLB44Mb7jzqQMu-8OJxtuPTmE3FLkq4ebg.webp"
            },
            {
              "id": 2,
              "name": "아이다",
              "price": 700,
              "imageUrl": "https://i.namu.wiki/i/NHwDBf6H1jECcAe5OMq2EGGW5UQkt1gYITM9usAr0LZCvlsHl7h69IgP-xU2jKK-GnF2M3ZDHBYx6qJwI8rb4A.webp"
            },
            {
              "id": 3,
              "name": "설백",
              "price": 1000,
              "imageUrl": "https://i.namu.wiki/i/JYqF9aBMf6foB0HMqfc8oZVXlUJjCUrK6W_5Q1Prk5YM2VA7nmIv57EVRDFPaQ2CKQubfsg-3BSgxt_6GXoMqw.webp"
            }
            ]
            """.allSpaceTrim(),
        )

        mockProductServer.shutDown()
    }

    @Test
    fun `잘못된 url 로 호출 시 응답 코드 404를 반환한다`() {
        // given:
        mockProductServer.start(1234)
        val client = OkHttpClient()

        val request =
            Request
                .Builder()
                .url("http://localhost:1234/product")
                .build()

        // when:
        val response = client.newCall(request).execute()

        // then:
        assertThat(response.code).isEqualTo(404)
    }

    private fun String.allSpaceTrim(): String = replace("\\s".toRegex(), "")

    @AfterEach
    fun tearDown() {
        mockProductServer.shutDown()
    }
}
