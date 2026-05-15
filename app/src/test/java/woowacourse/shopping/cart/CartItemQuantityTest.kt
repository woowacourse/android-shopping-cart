package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.shopping.domain.cart.model.CartItemQuantity

class CartItemQuantityTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, -2, -100, -1000, -2000, -3000])
    fun `개수의 값이 음수일 경우 예외가 발생한다`(value: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            CartItemQuantity(value)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 3, 10, 100, 13120])
    fun `개수의 값이 0 이상일 경우 예외가 발생하지 않는다`(value: Int) {
        assertTrue(CartItemQuantity.isValueValid(value))
    }
}
