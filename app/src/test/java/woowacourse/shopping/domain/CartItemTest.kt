package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartItemTest {
    private val productA = Product(
        name = "상품A",
        price = Money(2000),
        imageUrl = "",
        id = "1",
    )

    private val productB = Product(
        name = "상품B",
        price = Money(1000),
        imageUrl = "",
        id = "2",
    )

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 같으면 true를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val cartItem = CartItem(productA, Quantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartItem.hasProductId("1")

        // then : true를 반환한다
        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 다르면 false를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val cartItem = CartItem(productA, Quantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartItem.hasProductId("2")

        // then : false를 반환한다
        assertEquals(false, result)
    }

    @Test
    fun `현재 수량에 새로운 수량을 더한 CartItem을 반환한다`() {
        val quantity = Quantity(5)
        val cartItem = CartItem(
            product = productA,
            quantity = Quantity(1),
        )

        val result = cartItem.increase(quantity)
        val expect = CartItem(
            product = productA,
            quantity = Quantity(6),
        )
        assertEquals(expect, result)
    }

    @Test
    fun `현재 수량에 새로운 수량을 뺀 CartItem을 반환한다`() {
        val quantity = Quantity(1)
        val cartItem = CartItem(
            product = productA,
            quantity = Quantity(5),
        )

        val result = cartItem.decrease(quantity)
        val expect = CartItem(
            product = productA,
            quantity = Quantity(4),
        )
        assertEquals(expect, result)
    }
}
