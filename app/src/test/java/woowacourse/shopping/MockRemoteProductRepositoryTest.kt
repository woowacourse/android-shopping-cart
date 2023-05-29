package woowacourse.shopping

import io.mockk.every
import io.mockk.mockk
import model.Product
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.MockProductRemoteService
import woowacourse.shopping.database.MockRemoteProductRepositoryImpl
import woowacourse.shopping.database.product.MockProduct
import woowacourse.shopping.database.product.ProductRepository
import java.lang.Thread.sleep

class MockRemoteProductRepositoryTest {
    private lateinit var mockRemoteProductRepository: ProductRepository
    private lateinit var mockProductRemoteService: MockProductRemoteService

    @Before
    fun init() {
        mockProductRemoteService = mockk()
        mockRemoteProductRepository =
            MockRemoteProductRepositoryImpl(mockProductRemoteService)
    }

    @Test
    fun `처음 20개의 상품을 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        every {
            mockProductRemoteService.request(
                lastProductId = 0,
                onSuccess = any(),
                onFailure = any()
            )
        } answers {
            secondArg<(List<Product>) -> Unit>().invoke(MockProduct.products.take(20))
        }

        // when
        mockRemoteProductRepository.loadProducts(
            0,
            onSuccess = {
                actual = it
            },
            onFailure = {},
        )
        sleep(1000)

        // then
        val expected = MockProduct.products.subList(0, 20)
        assertEquals(expected, actual)
    }

    @Test
    fun `두번째 20개의 상품을 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        every {
            mockProductRemoteService.request(
                lastProductId = 20,
                onSuccess = any(),
                onFailure = any()
            )
        } answers {
            secondArg<(List<Product>) -> Unit>().invoke(MockProduct.products.subList(20, 40))
        }

        // when
        mockRemoteProductRepository.loadProducts(
            20,
            onSuccess = {
                actual = it
            },
            onFailure = {},
        )
        sleep(1000)

        // then
        val expected = MockProduct.products.subList(20, 40)
        assertEquals(expected, actual)
    }

    @Test
    fun `세번째 페이지의 상품들을 불러온다`() {
        // given
        var actual: List<Product> = listOf()
        every {
            mockProductRemoteService.request(
                lastProductId = 40,
                onSuccess = any(),
                onFailure = any()
            )
        } answers {
            secondArg<(List<Product>) -> Unit>().invoke(
                MockProduct.products.subList(
                    40,
                    MockProduct.products.size
                )
            )
        }

        // when
        mockRemoteProductRepository.loadProducts(
            40,
            onSuccess = {
                actual = it
            },
            onFailure = {},
        )
        sleep(1000)

        // then
        val expected =
            MockProduct.products.subList(40, MockProduct.products.size)
        assertEquals(expected, actual)
    }
}
