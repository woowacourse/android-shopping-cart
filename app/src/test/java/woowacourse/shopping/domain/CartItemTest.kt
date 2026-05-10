package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartItemTest {
    @Test
    fun `카트아이템은 상품 아이디와 개수를 갖는다`() {
        assertDoesNotThrow {
            CartItem(
                productId = "1",
                amount = 2,
            )
        }
    }

    @Test
    fun `카트아이템의 개수가 0개 이하라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            CartItem(
                productId = "1",
                amount = 0,
            )
        }
    }
}
