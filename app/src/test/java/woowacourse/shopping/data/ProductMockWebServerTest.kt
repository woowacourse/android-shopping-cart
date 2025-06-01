package woowacourse.shopping.data

import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.ProductMockWebServer
import woowacourse.shopping.data.remote.ProductMockWebServerDispatcher
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

class ProductMockWebServerTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var productService: ProductMockWebServer

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = ProductMockWebServerDispatcher
        mockWebServer.start(1999)
        productService = ProductMockWebServer()
    }

    @Test
    fun `모든 상품을 가져올 수 있다`() {
        val products = productService.fetchProducts()

        assertThat(products).isEqualTo(DummyProducts.values)
    }

    @Test
    fun `상품의 id로 상품을 가져올 수 있다`() {
        val product = productService.fetchProductById(0)

        assertThat(product).isEqualTo(
            Product(
                0,
                "맥심 모카골드 마일드",
                Price(12000),
                "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
            ),
        )
    }

    @Test
    fun `페이지_수_만큼_상품을_가져온다`() {
        val product = productService.fetchPagingProducts(0, 5)

        assertThat(product).isEqualTo(DummyProducts.values.take(5))
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }
}
