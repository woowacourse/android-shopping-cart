package woowacourse.shopping.data.datasource

import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.datasource.DefaultCart.addCartItem
import woowacourse.shopping.data.datasource.DefaultCart.getCartItem
import woowacourse.shopping.data.datasource.DefaultCart.plusCartItem
import woowacourse.shopping.data.datasource.DefaultCart.removeAllCartItem
import woowacourse.shopping.data.datasource.DefaultCart.minusCartItem
import woowacourse.shopping.data.model.CartItem

class DefaultCartTest {
    @Test
    fun `장바구니에 담은 아이템을 하나 가져올 수 있다`() {
        mockkObject(DefaultCart) {
            every { getCartItem(any()) } returns CartItem(1, 1, 1)
            val firstItem = getCartItem(1)

            assertThat(firstItem).isEqualTo(CartItem(1, 1, 1))
        }
    }

    @Test
    fun `장바구니에 상품을 추가할 수 있다`() {
        mockkObject(DefaultCart) {
            every { addCartItem(any(), any()) } returns 1
            val firstItemId = addCartItem(1, 1)

            assertThat(firstItemId).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 상품 수량을 늘릴 수 있다`() {
        mockkObject(DefaultCart) {
            every { plusCartItem(any(), any()) } returns 1
            val firstItemId = plusCartItem(1, 1)

            assertThat(firstItemId).isEqualTo(1)
        }
    }

    @Test
    fun `장바구니에 상품 수량을 줄일 수 있다`() {
        mockkObject(DefaultCart) {
            every { minusCartItem(any(), any()) } returns 1
            plusCartItem(1, 1)
            val secondItemId = minusCartItem(1, 1)

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
