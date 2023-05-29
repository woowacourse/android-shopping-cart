package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

internal class CartProductTest {

    @Test
    fun 수량이_2개면_감소된_수량은_1이다() {
        // given
        val cartProduct = createCartProduct(amount = 2)

        // when
        val actualCartProduct = cartProduct.decreaseAmount()
        val actual = actualCartProduct.amount

        // then
        val expected = 1
        assertEquals(expected, actual)
    }

    @Test
    fun 수량이_1개면_증가한_수량은_2이다() {
        // given
        val cartProduct = createCartProduct(amount = 1)

        // when
        val actualCartProduct = cartProduct.increaseAmount()
        val actual = actualCartProduct.amount

        // then
        val expected = 2
        assertEquals(expected, actual)
    }

    @Test
    fun 체크_상태를_참으로_바꾸면_참이다() {
        // given
        val cartProduct = createCartProduct()

        // when
        val actualCartProduct = cartProduct.changeChecked(true)
        val actual = actualCartProduct.isChecked

        // then
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun 가격이_1000원이고_수량이_2개인_상품의_가격은_2000원이다() {
        // given

        // when
        val cartProduct = CartProduct(
            time = LocalDateTime.now(),
            amount = 2,
            isChecked = true,
            product = Product(URL(""), title = "", price = 1000)
        )
        val actual = cartProduct.price

        // then
        val expected = 2000
        assertEquals(expected, actual)
    }
}
