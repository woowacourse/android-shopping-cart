package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class CartTest {

    @Test
    fun 장바구니에_상품을_추가하면_상품이_담긴다() {
        // given
        val cart = Cart(emptyList())

        // when
        val time = LocalDateTime.now()
        val product = createCartProduct(time = time)
        val actual = cart.add(product)

        // then
        val expected = Cart(listOf(createCartProduct(time = time)))
        assertEquals(expected, actual)
    }

    @Test
    fun 장바구니에서_상품을_삭제하면_상품이_사라진다() {
        // given
        val cart = Cart(listOf(createCartProduct()))

        // when
        val product = createCartProduct()
        val actual = cart.remove(product)

        // then
        val expected = Cart(emptyList())
        assertEquals(expected, actual)
    }
}
