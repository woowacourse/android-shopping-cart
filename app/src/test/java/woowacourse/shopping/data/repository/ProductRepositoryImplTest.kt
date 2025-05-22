package woowacourse.shopping.data.repository

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2

class ProductRepositoryImplTest {
    private lateinit var productRepository: ProductRepositoryImpl

    @BeforeEach
    fun setUp() {
        productRepository = mockk()
    }

    @Test
    fun `상품을_ID로_가져올_수_있다`() {
        val productId = 1L
        val expectedProduct = productFixture1

        every { productRepository.get(productId, any()) } answers {
            val callback = secondArg<(Product) -> Unit>()
            callback(expectedProduct)
        }

        var result: Product? = null
        productRepository.get(productId) { product ->
            result = product
        }

        assertEquals(expectedProduct, result)
        verify { productRepository.get(productId, any()) }
    }

    @Test
    fun `단일_페이지를_불러올_수_있다`() {
        val page = 0
        val pageSize = 2
        val expectedPage =
            ProductSinglePage(
                products = listOf(productFixture1, productFixture2),
                hasNextPage = false,
            )

        every { productRepository.loadSinglePage(page, pageSize, any()) } answers {
            val callback = thirdArg<(ProductSinglePage) -> Unit>()
            callback(expectedPage)
        }

        var result: ProductSinglePage? = null
        productRepository.loadSinglePage(page, pageSize) { page ->
            result = page
        }

        assertEquals(expectedPage, result)
        verify { productRepository.loadSinglePage(page, pageSize, any()) }
    }

    @AfterEach
    fun tearDown() {
        clearMocks(productRepository)
    }
}
