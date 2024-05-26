package woowacourse.shopping.data

import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.entity.ProductEntity.Companion.STUB_LIST
import woowacourse.shopping.data.service.ProductDispatcher
import woowacourse.shopping.data.service.ProductMockServer
import woowacourse.shopping.data.service.ProductService

class ProductMockServerTest {
    private val productMockServer: MockWebServer = ProductMockServer.instance(ProductDispatcher())
    private val productService: ProductService = ProductService(productMockServer)

    @After
    fun tearDown() {
        ProductMockServer.shutDown()
    }

    @Test
    @DisplayName("product 전체를 꺼낼 수 있다")
    fun can_get_all_product_list() {
//        val productUrl = "/products"
//        val expectedResponse = Json.encodeToString(ProductEntity.STUB_LIST)
//
//        // OkHttpClient 설정 - 타임아웃 설정
//        val client = OkHttpClient.Builder()
//            .connectTimeout(4, TimeUnit.SECONDS)
//            .readTimeout(4, TimeUnit.SECONDS)
//            .writeTimeout(4, TimeUnit.SECONDS)
//            .build()
//
//        val request =
//            Request.Builder().url("${shoppingMockServer.url(productUrl)}").build()
//        val call = client.newCall(request)
//        val response = call.execute()
//
//        val responseBody = response.body?.string()
//        assertThat(responseBody).isEqualTo(expectedResponse)
//
//        val recordedRequest = shoppingMockServer.takeRequest()
//        assertThat(recordedRequest.path).isEqualTo(productUrl)
    }

    @Test
    @DisplayName("product 전체를 꺼낼 수 있다")
    fun can_get_all_product_list2() {
        // given
        val actual = productService.products()

        // then
        assertThat(actual).isEqualTo(STUB_LIST)
    }
}
