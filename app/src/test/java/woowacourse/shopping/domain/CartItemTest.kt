package woowacourse.shopping.domain

import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.fixture.ShoppingFixture

class CartItemTest {
    @Test
    fun `카트아이템은 상품 목록과 개수를 갖는다`() {
        assertDoesNotThrow {
            CartItem(
                product = ShoppingFixture.getProduct(),
                quantity = 2,
            )
        }
    }

    @Test
    fun `카트아이템은 상품의 총 가격을 안다`() {
        val cartItem =
            CartItem(
                product = ShoppingFixture.getProduct(),
                quantity = 2,
            )

        cartItem.totalPrice shouldEqual Price(20_000)
    }

    @Test
    fun `카트아이템의 개수가 0개 이하라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            CartItem(
                product = ShoppingFixture.getProduct(),
                quantity = 0,
            )
        }
    }
}
