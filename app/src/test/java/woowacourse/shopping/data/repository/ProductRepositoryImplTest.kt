package woowacourse.shopping.data.repository

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2

class ProductRepositoryImplTest {
    private val storage: ProductStorage = mockk()
    private val productRepository = ProductRepositoryImpl(storage)

    @Test
    fun `Id에_해당하는_상품을_가져온다`() {
        // given
        val productId = 1L
        val expectedProduct = productFixture1
        every { storage[productId] } returns expectedProduct

        // when
        val actualProduct = productRepository[productId]

        // then
        assertEquals(expectedProduct, actualProduct)
        verify(exactly = 1) { storage[productId] }
    }

    @Test
    fun `첫_번째_페이지의_상품을_가져온다`() {
        // given
        val page = 0
        val pageSize = 10
        val fromIndex = 0
        val toIndex = fromIndex + pageSize
        val excepted =
            ProductSinglePage(
                listOf(
                    productFixture1,
                    productFixture2,
                ),
                false,
            )

        every { storage.singlePage(fromIndex, toIndex) } returns excepted

        // when
        val actualProducts = productRepository.loadSinglePage(page, pageSize)

        // Then
        assertEquals(excepted, actualProducts)
        verify(exactly = 1) { storage.singlePage(fromIndex, toIndex) }
    }

    @Test
    fun `빈_페이지를_요청하면_빈_리스트를_반환한다`() {
        // given
        val page = 10
        val pageSize = 20
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize

        every { storage.singlePage(fromIndex, toIndex) } returns
            ProductSinglePage(
                emptyList(),
                true,
            )

        // when
        val actual = productRepository.loadSinglePage(page, pageSize)

        // then
        assertTrue(actual.products.isEmpty())
        assertTrue(actual.hasNextPage)
        verify(exactly = 1) { storage.singlePage(fromIndex, toIndex) }
    }

    @Test
    fun `modifyQuantity()는 productStorage의 modifyQuantity()를 호출해야 한다`() {
        // given
        val id = 1L
        val quantity = 5

        every { storage.modifyQuantity(id, Quantity(5)) } just Runs

        // when
        productRepository.modifyQuantity(id, quantity)

        // then
        verify(exactly = 1) { storage.modifyQuantity(id, Quantity(quantity)) }
    }
}
