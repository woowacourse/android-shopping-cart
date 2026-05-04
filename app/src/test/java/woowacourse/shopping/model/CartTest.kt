@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.model

import java.util.UUID
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartTest {
    private val productId = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000001"))

    @Test
    fun `수량이 1 이상인 항목으로 장바구니를 생성할 수 있다`() {
        assertDoesNotThrow {
            Cart(mapOf(productId to 1))
        }
    }

    @Test
    fun `수량이 0인 항목이 있으면 장바구니를 생성할 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Cart(mapOf(productId to 0))
        }
    }

    @Test
    fun `수량이 음수인 항목이 있으면 장바구니를 생성할 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Cart(mapOf(productId to -1))
        }
    }
}
