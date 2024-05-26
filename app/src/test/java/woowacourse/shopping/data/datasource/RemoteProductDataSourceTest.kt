package woowacourse.shopping.data.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_A
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_B
import woowacourse.shopping.data.DummyData.STUB_PRODUCT_C
import woowacourse.shopping.data.datasource.product.RemoteProductDataSource
import woowacourse.shopping.data.remote.api.DefaultProductApi
import woowacourse.shopping.data.remote.api.ProductApi
import woowacourse.shopping.domain.model.Product

class RemoteProductDataSourceTest {
    private val api: ProductApi = DefaultProductApi(OkHttpClient())
    private val remoteProductDataSource: RemoteProductDataSource = RemoteProductDataSource(api)

    @Test
    fun `하나의 product 읽기`() {
        val actual = remoteProductDataSource.findProductById(1).getOrThrow()
        val expected = STUB_PRODUCT_A
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `offset과_size로_product_list_찾기`() {
        val actual = remoteProductDataSource.getOffsetRanged(1, 3).getOrThrow()
        val expected = listOf(STUB_PRODUCT_A, STUB_PRODUCT_B, STUB_PRODUCT_C)
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {
            startMockWebServer()
        }

        @JvmStatic
        private fun startMockWebServer() {
            val mockWebServer = MockWebServer()
            mockWebServer.start(12345)
            val gson = Gson()
            val products = listOf(STUB_PRODUCT_A, STUB_PRODUCT_B, STUB_PRODUCT_C)
            val type = object : TypeToken<List<Product>>() {}.type

            val productsJson = gson.toJson(products, type)
            val productJson = gson.toJson(STUB_PRODUCT_A)

            val dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        println(request.path)
                        return when (request.path) {
                            "/paging-product/3/1" -> {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                                    .setBody(productsJson)
                            }

                            "/products/1" -> {
                                MockResponse()
                                    .setHeader("Content-Type", "application/json")
                                    .setResponseCode(200)
                                    .setBody(productJson)
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
}
