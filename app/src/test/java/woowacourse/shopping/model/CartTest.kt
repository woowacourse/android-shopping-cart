@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartTest {
    val product1 = Product(
        name = "새우깡",
        price = Money(3100),
        imageUrl = ""
    )

    val product2 = Product(
        name = "아이셔",
        price = Money(1300),
        imageUrl = ""
    )
    val items = mapOf(product1 to 1)

    @Test
    fun `정상적인 상품 객체를 장바구니에 추가하면, 장바구니 내부 목록에 해당 상품이 올바르게 반영된다`() {
        val cart = Cart(items).add(product2)
        val result1 = cart.showAll().contains(product1)
        val result2 = cart.showAll().contains(product2)

        assert(result1 && result2)
    }

    @Test
    fun `장바구니에 존재하는 상품을 삭제 요청하면, 목록에서 해당 상품이 정상적으로 제거된다`() {
        val cart = Cart(items).delete(product1)
        val result = cart.showAll().containsKey(product1)

        assert(!result)
    }

    @Test
    fun `존재하지 않는 상품의 삭제를 시도할 경우, 상태가 변경되지 않거나 올바르게 무시,예외 처리된다`() {
        assertThrows<IllegalArgumentException> { Cart(items).delete(product2) }
    }
}
