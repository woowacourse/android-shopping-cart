package woowacourse.shopping.data.repository

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage
import woowacourse.shopping.fixture.cartFixture1
import woowacourse.shopping.fixture.cartFixture2
import woowacourse.shopping.fixture.cartFixture3

class CartRepositoryImplTest {
    private val mockStorage: CartStorage = mockk()
    private val repository = CartRepositoryImpl(mockStorage)

    @Test
    fun `insert()는 수량 1로 Cart를 생성하여 저장한다`() {
        // given
        val productId = 10L
        val expectedCart = Cart(productId = productId, quantity = Quantity(1))

        every { mockStorage.insert(expectedCart) } just Runs

        // when
        repository.insert(productId)

        // then
        verify(exactly = 1) { mockStorage.insert(expectedCart) }
    }

    @Test
    fun `첫_번째_페이지의_장바구니_상품을_반환한다`() {
        // given
        val page = 0
        val pageSize = 3
        val fromIndex = 0
        val toIndex = 3

        val expected =
            CartSinglePage(
                carts = listOf(cartFixture1, cartFixture2, cartFixture3),
                hasNextPage = true,
            )
        every { mockStorage.singlePage(fromIndex, toIndex) } returns expected

        // when
        val result = repository.loadSinglePage(page, pageSize)

        // then
        assertEquals(expected, result)
        verify { mockStorage.singlePage(fromIndex, toIndex) }
    }

    @Test
    fun `다음_페이지의_상품이_없으면_hasNextPage는_false를_반환한다`() {
        // given
        val page = 2
        val pageSize = 5
        val fromIndex = 10
        val toIndex = 15

        val expected =
            CartSinglePage(
                carts = listOf(cartFixture1),
                hasNextPage = false,
            )
        every { mockStorage.singlePage(fromIndex, toIndex) } returns expected

        // when
        val result = repository.loadSinglePage(page, pageSize)

        // then
        assertFalse(result.hasNextPage)
        assertEquals(expected.carts, result.carts)
        verify { mockStorage.singlePage(fromIndex, toIndex) }
    }

    @Test
    fun `modifyQuantity()는 해당 상품의 수량을 변경한다`() {
        // given
        val productId = 5L
        val quantity = Quantity(3)
        every { mockStorage.modifyQuantity(productId, quantity) } just Runs

        // when
        repository.modifyQuantity(productId, quantity)

        // then
        verify(exactly = 1) { mockStorage.modifyQuantity(productId, quantity) }
    }
}
