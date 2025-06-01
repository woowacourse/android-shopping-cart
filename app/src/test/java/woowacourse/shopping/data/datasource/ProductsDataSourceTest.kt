package woowacourse.shopping.data.datasource

import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.network.MockingServer
import woowacourse.shopping.data.network.ProductService
import woowacourse.shopping.data.storage.ProductStorage

class ProductsDataSourceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var productService: ProductService
    private lateinit var dataSource: ProductsDataSource

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        productService = MockingServer()
        dataSource = ProductsDataSource(productService)
    }

    @Test
    fun `id에 해당하는 상품을 가져온다`() {
        // given
        val productId = 1L
        val expectedProduct = ProductStorage[1]

        // when
        val result = dataSource.getProduct(productId)

        // then
        assertEquals(expectedProduct, result)
    }

    @Test
    fun `상품 id에 해당하는 상품 목록을 가져온다`() {
        // given
        val productIds = listOf(1L, 2L, 3L)
        val expected = ProductStorage.getProductsById(productIds)

        // when
        val result = dataSource.getProducts(productIds)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `첫 번째 페이지에 해당하는 상품을 가져온다`() {
        // given
        val from = 0
        val to = 2
        val expected = ProductStorage.singlePage(from, to)

        // when
        val result = dataSource.singlePage(from, to)

        // then
        assertEquals(expected, result)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
