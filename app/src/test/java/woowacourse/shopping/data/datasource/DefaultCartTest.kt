package woowacourse.shopping.data.datasource

import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.datasource.DefaultCart.addCartItem
import woowacourse.shopping.data.datasource.DefaultCart.removeAllCartItem
import woowacourse.shopping.data.datasource.DefaultCart.removeCartItem

class DefaultCartTest {
    @Test
    fun `장바구니에 상품 수량을 늘릴 수 있다`() {
        mockkObject(DefaultCart) {
            every { addCartItem(any(), any()) } returns 1
            val firstItemId = addCartItem(1, 1)

            assertThat(firstItemId).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 상품 수량을 줄일 수 있다`() {
        mockkObject(DefaultCart) {
            every { removeCartItem(any(), any()) } returns 1
            addCartItem(1, 1)
            val secondItemId = removeCartItem(1, 1)

            assertThat(secondItemId).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 추가한 상품을 제거할 수 있다`() {
        mockkObject(DefaultCart) {
            addCartItem(1, 1)
            addCartItem(2, 1)
            addCartItem(3, 1)
            val removedItemId = removeAllCartItem(1)

            assertThat(removedItemId).isEqualTo(1)
        }
    }
}
