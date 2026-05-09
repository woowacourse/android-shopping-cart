package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {
    private val product1 =
        Product(
            id = 1L,
            name = ProductName("상품1"),
            price = Money(5000),
            imageUrl = "",
        )

    private val product2 =
        Product(
            id = 2L,
            name = ProductName("상품2"),
            price = Money(1000),
            imageUrl = "",
        )

    @Test
    fun `빈 카트는 size는 0이다`() {
        val cart = Cart()
        assertThat(cart.size).isEqualTo(0)
    }

    @Test
    fun `빈 카트의 totalPrice는 0원이다`() {
        val cart = Cart()
        assertThat(cart.totalPrice).isEqualTo(Money(0))
    }

    @Test
    fun `카트의 상품 품목이 2개 들어있다면 size는 2를 반환한다`() {
        val cart =
            Cart(
                listOf(
                    CartItem(product = product1, quantity = 1),
                    CartItem(product = product2, quantity = 30),
                ),
            )
        assertThat(cart.size).isEqualTo(2)
    }

    @Test
    fun `totalPrice가 상품 전체의 가격을 계산한다`() {
        val cart =
            Cart(
                listOf(
                    CartItem(product = product1, quantity = 1),
                    CartItem(product = product2, quantity = 30),
                ),
            )

        assertThat(cart.totalPrice).isEqualTo(Money(35000))
    }
}
