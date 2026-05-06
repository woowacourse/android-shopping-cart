package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CartItemTest {
    private val product =
        Product(
            name = ProductName(name = "상품"),
            price = Money(amount = 5000),
            imageUrl = "",
        )

    @Test
    fun `해당 상품의 개수에 따른 가격을 계산한다`() {
        val item =
            CartItem(
                product = product,
                quantity = 2,
            )
        assertThat(item.getTotalPrice()).isEqualTo(Money(10000))
    }

    @Test
    fun `상품 개수가 0 이하일 시 예외를 반환한다`() {
        assertThatThrownBy {
            CartItem(
                product = product,
                quantity = 0,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `상품 개수가 100개 이상 일 시 예외를 반환한다`() {
        assertThatThrownBy {
            CartItem(
                product = product,
                quantity = 100,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
