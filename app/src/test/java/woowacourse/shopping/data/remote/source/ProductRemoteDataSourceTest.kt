package woowacourse.shopping.data.remote.source

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import woowacourse.shopping.data.remote.api.ShoppingMockServer

class ProductRemoteDataSourceTest {
    private lateinit var dataSource: ProductRemoteDataSource

    @BeforeEach
    fun setUp() {
        ShoppingMockServer.start()
        dataSource = ProductRemoteDataSource()
    }

    @AfterEach
    fun tearDown() {
        ShoppingMockServer.shutdown()
    }

    @Test
    fun `페이지에 해당하는 상품 목록을 size만큼 가져온다`() = runBlocking {
        val result = dataSource.getProducts(page = 1, size = 20)

        assertEquals(true, result.isSuccess)

        val products = result.getOrNull()
        assertNotNull(products)
        assertTrue(products.isNotEmpty())
    }

    @Test
    fun `특정 상품 정보를 가져온다`() = runBlocking {
        val targetId = "1"
        val result = dataSource.getProduct(targetId)

        assertTrue(result.isSuccess)

        val productResponse = result.getOrNull()
        assertNotNull(productResponse)
        assertEquals(1, productResponse.id)
    }
}
