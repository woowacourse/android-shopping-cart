package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CartItemTest {
    @Test
    fun `선택한 상품의 수량을 늘릴 수 있다`() {
        val cartItem = CartItem(createProduct(), 1)

        val newCartItem = cartItem.increaseQuantity()

        assertThat(newCartItem.count).isEqualTo(2)
    }

    @Test
    fun `선택한 상품의 수량을 줄일 수 있다`() {
        val cartItem = CartItem(createProduct(), 1)

        val newCartItem = cartItem.decreaseQuantity()

        assertThat(newCartItem.count).isEqualTo(0)
    }

    @Test
    fun `상품의 수량은 0보다 작아질 수 없다`() {
        val cartItem = CartItem(createProduct(), 0)

        val newCartItem = cartItem.decreaseQuantity()

        assertThat(newCartItem.count).isEqualTo(0)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createProduct(): Product =
        Product(
            productId = Uuid.random(),
            imageUrl = "",
            productName = "동원 스위트콘",
            price = Price(99800),
        )
}
