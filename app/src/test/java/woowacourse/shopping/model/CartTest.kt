@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

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

    @Test
    fun `상품을 추가하면 해당 상품의 수량이 1 증가한 새 장바구니를 반환한다`() {
        val cart = Cart(emptyMap())

        val actual = cart.add(productId)

        assertEquals(1, actual.items[productId])
    }

    @Test
    fun `상품을 삭제하면 해당 상품의 수량이 1 감소한 새 장바구니를 반환한다`() {
        val cart = Cart(mapOf(productId to 2))

        val actual = cart.delete(productId)

        assertEquals(1, actual.items[productId])
    }

    @Test
    fun `수량이 1인 상품을 삭제하면 장바구니에서 제거된다`() {
        val cart = Cart(mapOf(productId to 1))

        val actual = cart.delete(productId)

        assertEquals(emptyMap<ProductId, Int>(), actual.items)
    }
}
