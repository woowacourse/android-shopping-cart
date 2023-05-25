package woowacource.shopping.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import java.time.LocalDateTime

internal class CartItemTest {

    @Test
    fun `장바구니 아이템의 아이디가 같으면 같다고 판단한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val oneCartItem = CartItem(product, LocalDateTime.MAX, 1).apply { id = 1L }
        val otherCartItem = CartItem(product, LocalDateTime.MIN, 2).apply { id = 1L }

        assertThat(oneCartItem).isEqualTo(otherCartItem)
    }

    @Test
    fun `장바구니 아이템의 아이디가 다르면 다르다고 판단한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val oneCartItem = CartItem(product, LocalDateTime.MAX, 1).apply { id = 1L }
        val otherCartItem = CartItem(product, LocalDateTime.MAX, 1).apply { id = 2L }

        assertThat(oneCartItem).isNotEqualTo(otherCartItem)
    }

    @Test
    fun `장바구니 아이템의 아이디가 널일 때 아이디를 널이 아닌 값으로 설정하면 아이디가 설정된다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val cartItem = CartItem(product, LocalDateTime.MAX, 1)

        cartItem.id = 1L

        assertThat(cartItem.id).isEqualTo(1L)
    }

    @Test
    fun `장바구니 아이템의 아이디가 널이 아닐 때 아이디를 널이 아닌 값으로 설정하면 아이디가 설정되지 않는다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val cartItem = CartItem(product, LocalDateTime.MAX, 1).apply { id = 1L }

        cartItem.id = 2L

        assertThat(cartItem.id).isEqualTo(1L)
    }

    @Test
    fun `장바구니 아이템의 주문 금액은 상품의 가격에 수량을 곱한 값이다`() {
        val priceOfProduct = 10000
        val product = Product(1L, "imageUrl1", "name1", priceOfProduct)
        val count = 5
        val cartItem = CartItem(product, LocalDateTime.MAX, count)

        assertThat(cartItem.getOrderPrice()).isEqualTo(50000)
    }

    @Test
    fun `장바구니 아이템의 수량을 더하면 수량이 1 증가한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val cartItem = CartItem(product, LocalDateTime.MAX, 1)

        cartItem.plusCount()

        assertThat(cartItem.count).isEqualTo(2)
    }

    @Test
    fun `장바구니 아이템의 수량이 1일 때 수량을 빼면 수량이 변하지 않는다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val cartItem = CartItem(product, LocalDateTime.MAX, 1)

        cartItem.minusCount()

        assertThat(cartItem.count).isEqualTo(1)
    }

    @Test
    fun `장바구니 아이템의 수량이 2 이상일 때 수량을 빼면 수량이 1 감소한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val cartItem = CartItem(product, LocalDateTime.MAX, 2)

        cartItem.minusCount()

        assertThat(cartItem.count).isEqualTo(1)
    }
}