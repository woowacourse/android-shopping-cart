package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `장바구니에 제품을 추가할 수 있다`() {
        // given
        val cart = Cart()
        val product = Product("테스트", "이미지", 10000)

        // when
        cart.addProduct(product)

        // then
        assertThat(cart.products).contains(product)
    }

    @Test
    fun `장바구니의 제품을 삭제할 수 있다`() {
        // given
        val product = Product("테스트", "이미지", 10000)
        val cart = Cart(mutableListOf(product))

        // when
        cart.removeProduct(product)

        // then
        assertThat(cart.products).doesNotContain(product)
    }
}
