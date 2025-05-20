package woowacourse.shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductTest {
    @Test
    fun `상품의 가격은 0보다 작으면 안된다`() {
        assertThrows<IllegalArgumentException> {
            Product(0, "", "아메리카노", -100)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 100])
    fun `상품의 가격은 0보다 크면 된다`(price: Int) {
        assertDoesNotThrow {
            Product(0, "", "아메리카노", price)
        }
    }
}
