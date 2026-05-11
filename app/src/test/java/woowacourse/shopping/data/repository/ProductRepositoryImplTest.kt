package woowacourse.shopping.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.remote.MockServer

class ProductRepositoryImplTest {
    private val repository = ProductRepositoryImpl

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp() {
            MockServer.start(blocking = true)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            MockServer.stop()
        }
    }

    @Test
    fun `100개의 리스트에서 0번째 페이지와 20 페이지 사이즈를 호출하면 20개의 응답이 온다`() = runTest {
        val result = repository.getProducts(0, 20)
        assertEquals(20, result.items.size)
    }

    @Test
    fun `1번째 페이지 요청 시 다음 20개 상품이 반환된다`() = runTest {
        val result = repository.getProducts(1, 20)
        assertEquals(20, result.items.size)
    }

    @Test
    fun `마지막 페이지 이후 요청 시 빈 목록이 반환된다`() = runTest {
        val result = repository.getProducts(10, 20)

        assertEquals(0, result.items.size)
    }

    @Test
    fun `잘못된 페이지 번호 요청 시 예외가 발생한다`() = runTest {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getProducts(-1, 20)
        }
    }

    @Test
    fun `잘못된 페이지 사이즈 요청 시 예외가 발생한다`() = runTest {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getProducts(0, 0)
        }
    }

    @Test
    fun `남은 상품이 페이지 사이즈보다 적을 때 남은 상품만 반환된다`() = runTest {
        val result = repository.getProducts(3, 30)
        assertEquals(10, result.items.size)
    }

    @Test
    fun `존재하는 상품 ID로 getProduct를 호출하면 해당 상품을 반환한다`() = runTest {
        val product = repository.getProduct("product-1")
        assertEquals("product-1", product?.id)
        assertEquals("콜라 1", product?.productTitle?.value)
    }

    @Test
    fun `존재하지 않는 상품 ID로 getProduct를 호출하면 null을 반환한다`() = runTest {
        val product = repository.getProduct("non-existent")
        assertEquals(null, product)
    }

    @Test
    fun `서버 응답이 404인 경우 getProduct는 null을 반환한다`() = runTest {
        val product = repository.getProduct("non-existent")
        assertEquals(null, product)
    }

    @Test
    fun `네트워크 에러 발생 시 getProducts는 빈 리스트를 반환한다`() = runTest {
        // Given
        MockServer.stop()

        try {
            // When
            val result = repository.getProducts(0, 20)
            
            // Then
            assertEquals(0, result.items.size)
        } finally {
            MockServer.start(blocking = true)
        }
    }
}
