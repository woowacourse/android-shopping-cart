package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    @Test
    fun `장바구니는 모든 상품의 가격을 받아올 수 있다`() {
        val product1 = Product(1L, "아메리카노", 2500)
        val product2 = Product(1L, "롱블랙", 2300)

        val shoppingCart = ShoppingCart(listOf(product1, product2))

        val actual = shoppingCart.allPrice
        val expected = 4800

        assertThat(actual).isEqualTo(expected)
    }
}
