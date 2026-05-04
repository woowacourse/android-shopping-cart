package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartTest {
    @OptIn(ExperimentalUuidApi::class)
    val product =
        Product(
            productId = Uuid.random(),
            imageUrl = "",
            productName = "동원 스위트콘",
            price = Price(99800),
        )

    @Test
    fun `잗바구니에 상품을 추가할 수 있다`() {
        val cart = Cart()

        val newCart = cart.addProductToCart(product)

        assertThat(newCart.productAndCounts.size).isEqualTo(1)
    }

    @Test
    fun `각 상품에 대한 정보를 정상적으로 받아온다`() {
        val cart = Cart()

        val newCart = cart.addProductToCart(product)

        assertThat(newCart.productAndCounts[0].product).isEqualTo(product)
    }
}
