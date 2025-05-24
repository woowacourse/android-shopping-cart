package woowacourse.shopping.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.datasource.ProductsDataSource
import woowacourse.shopping.data.fake.FakeProductRepositoryImpl
import woowacourse.shopping.data.network.dto.ProductDto
import woowacourse.shopping.data.network.dto.ProductPageDto
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImplTest {
    private lateinit var dataSource: ProductsDataSource
    private lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = FakeProductRepositoryImpl(dataSource)
    }

    @Test
    fun `주어진_productId로_제품을_조회하면_해당_제품을_반환한다`() {
        // Given
        val productId = 1L
        val productDto = mockk<ProductDto>()
        val productDomain = mockk<Product>()
        every { dataSource.getProduct(productId) } returns productDto
        every { productDto.toDomain() } returns productDomain

        // When
        val product = repository.get(productId)

        // Then
        assertNotNull(product)
        assertEquals(productDomain, product)
        verify(exactly = 1) { dataSource.getProduct(productId) }
        verify(exactly = 1) { productDto.toDomain() }
    }

    @Test
    fun `상품_id_리스트를_전달하면_해당_제품_목록을_반환한다`() {
        // Given
        val productIds = listOf(1L, 2L)
        val productDtos = listOf(mockk<ProductDto>(), mockk())
        val productDomains = listOf(mockk<Product>(), mockk<Product>())
        every { dataSource.getProducts(productIds) } returns productDtos
        every { productDtos[0].toDomain() } returns productDomains[0]
        every { productDtos[1].toDomain() } returns productDomains[1]

        // When
        repository.getProducts(productIds) { products ->
            // Then
            assertEquals(2, products.size)
            assertEquals(productDomains, products)
            verify(exactly = 1) { dataSource.getProducts(productIds) }
            verify(exactly = 1) { productDtos[0].toDomain() }
            verify(exactly = 1) { productDtos[1].toDomain() }
        }
    }

    @Test
    fun `페이지_번호와_페이지_크기를_전달하면_해당_페이지의_제품_데이터를_반환한다`() {
        // Given
        val page = 0
        val pageSize = 2
        val fromIndex = 0
        val toIndex = 2
        val productPageDto = mockk<ProductPageDto>()
        val productSinglePageDomain = mockk<ProductSinglePage>()

        every { dataSource.singlePage(fromIndex, toIndex) } returns productPageDto
        every { productPageDto.toDomain() } returns productSinglePageDomain

        // When
        repository.loadSinglePage(page, pageSize) { productSinglePage ->
            // Then
            assertEquals(productSinglePageDomain, productSinglePage)
            verify(exactly = 1) { dataSource.singlePage(fromIndex, toIndex) }
            verify(exactly = 1) { productPageDto.toDomain() }
        }
    }
}
