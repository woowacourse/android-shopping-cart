package woowacourse.shopping.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.CartResult
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3

class CartRepositoryImplTest {
    private val mockStorage: CartStorage = mockk()
    private val repository = CartRepositoryImpl(mockStorage)

    @Test
    fun `첫_번째_페이지의_장바구니_상품을_반환한다`() {
        // given
        val products =
            listOf(
                productFixture1,
                productFixture2,
                productFixture3,
            )

        val expectedResult = CartResult(products, hasNextPage = true)

        every { mockStorage.singlePage(0, 3) } returns expectedResult

        // when
        val result = repository.loadSinglePage(page = 0, pageSize = 3)
        assertEquals(expectedResult, result)
        verify(exactly = 1) { mockStorage.singlePage(0, 3) }
    }

    @Test
    fun `다음_페이지의_상품이_없으면_hasNextPage는_false를_반환한다`() {
        // given
        val lastPageProducts =
            listOf(
                Product(id = 11, name = "짱맛있는 붕어빵", price = Price(11000), ""),
            )
        val expectedResult =
            CartResult(
                products = lastPageProducts,
                hasNextPage = false,
            )

        every { mockStorage.singlePage(10, 15) } returns expectedResult

        // when
        val result = repository.loadSinglePage(page = 2, pageSize = 5)

        // then
        assertEquals(false, result.hasNextPage)
        assertEquals(1, result.products.size)
        assertEquals(lastPageProducts, result.products)

        verify(exactly = 1) { mockStorage.singlePage(10, 15) }
    }
}
