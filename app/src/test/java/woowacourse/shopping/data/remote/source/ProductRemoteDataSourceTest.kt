package woowacourse.shopping.data.remote.source

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.data.remote.api.ShoppingMockServer

class ProductRemoteDataSourceTest {
    private val dataSource = ProductRemoteDataSource()

    @BeforeEach
    fun setUp() {
        ShoppingMockServer.start()
    }

    @AfterEach
    fun tearDown() {
        ShoppingMockServer.shutdown()
    }

    @Test
    fun `전체 상품 목록을 가져온다`() = runBlocking {
        val products = dataSource.getProducts()

        Assertions.assertEquals(MockData.MOCK_PRODUCTS_LIST.size, products.size)
        Assertions.assertEquals("품목1", products[0].name)
    }

    @Test
    fun `특정 상품 정보를 가져온다`() = runBlocking {
        val targetId = "1"

        val product = dataSource.getProduct(targetId)

        Assertions.assertEquals(1, product.id)
        Assertions.assertEquals("품목1", product.name)
    }
}
