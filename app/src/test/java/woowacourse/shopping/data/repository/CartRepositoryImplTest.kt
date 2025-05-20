package woowacourse.shopping.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.cart.CartSinglePage
import woowacourse.shopping.fixture.cartFixture1
import woowacourse.shopping.fixture.cartFixture2
import woowacourse.shopping.fixture.cartFixture3

class CartRepositoryImplTest {
    private val mockStorage: CartStorage = mockk()
    private val repository = CartRepositoryImpl(mockStorage)

    @Test
    fun `첫_번째_페이지의_장바구니_상품을_반환한다`() {
        // given
        val expectedResult =
            CartSinglePage(
                carts = listOf(cartFixture1, cartFixture2, cartFixture3),
                hasNextPage = true,
            )

        every { mockStorage.singlePage(0, 3) } returns expectedResult

        // when
        val result = repository.loadSinglePage(page = 0, pageSize = 3)

        // then
        assertEquals(expectedResult, result)
        verify(exactly = 1) { mockStorage.singlePage(0, 3) }
    }

    @Test
    fun `다음_페이지의_상품이_없으면_hasNextPage는_false를_반환한다`() {
        // given
        val cart = cartFixture1
        val expectedResult =
            CartSinglePage(
                carts = listOf(cart),
                hasNextPage = false,
            )

        every { mockStorage.singlePage(10, 15) } returns expectedResult

        // when
        val result = repository.loadSinglePage(page = 2, pageSize = 5)

        // then
        assertEquals(false, result.hasNextPage)
        assertEquals(1, result.carts.size)
        assertEquals(expectedResult.carts, result.carts)

        verify(exactly = 1) { mockStorage.singlePage(10, 15) }
    }
}
