package woowacourse.shopping.domain.mockServer

import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.mockserver.ProductHttpClient
import woowacourse.shopping.data.mockserver.startMockServer

class ProductHttpClientTest {
    private lateinit var server: MockWebServer
    private lateinit var client: ProductHttpClient

    @BeforeEach
    fun setUp() {
        server = startMockServer()
        client = ProductHttpClient(server.url("/").toString().removeSuffix("/"))
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `상품 목록을 받아온다`() {
        val products = client.getProductList()

        assertThat(products[0].name).isEqualTo("이상해씨")
    }
}
