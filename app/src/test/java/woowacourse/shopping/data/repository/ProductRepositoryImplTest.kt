package woowacourse.shopping.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import woowacourse.shopping.data.remote.api.ShoppingMockServer
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImplTest {
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        ShoppingMockServer.start()
        repository = ProductRepositoryImpl(ProductRemoteDataSource())
    }

    @AfterEach
    fun tearDown() {
        ShoppingMockServer.shutdown()
    }

    @Test
    fun `모든 상품 목록을 조회하면 상품 도메인 모델로 반환한다`() = runBlocking {
        val result = repository.getProducts(1, 5)
        assertEquals(true, result.isSuccess)

        val products = result.getOrNull()
        assertNotNull(products)
        assertEquals(5, products.size)

        assertEquals("품목1", products[0].name)
        assertEquals(Money(1000), products[0].price)
        assertEquals("1", products[0].id)
    }

    @Test
    fun `특정 상품을 조회하면 상품 도메인 모델로 반환한다`() = runBlocking {
        val targetId = "1"
        val result = repository.getProduct(targetId)

        assertEquals(true, result.isSuccess)

        val product = result.getOrNull()
        assertNotNull(product)
        assertEquals("품목1", product.name)
        assertEquals(Money(1000), product.price)
        assertEquals("1", product.id)
    }

    @Test
    fun `존재하지 않는 상품을 조회하면 fail을 반환한다`() = runBlocking {
        val targetId = "-11111"

        val result = repository.getProduct(targetId)
        assertEquals(true, result.isFailure)
    }
}
