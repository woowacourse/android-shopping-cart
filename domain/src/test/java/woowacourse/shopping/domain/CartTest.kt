package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class CartTest {
    @Test
    fun 체크되지_않은_상품을_선택하면_일치하는_상품을_찾아_체크를_참으로_변경한다() {
        // given
        val cart = makeCart(10, 1, "1" to false, "2" to false, "3" to false)

        // when
        val productToSelect = makeCheckableCartProduct(10, 1, "2" to false)
        val expect = cart.selectProduct(productToSelect, true)

        // then
        val actual = makeCart(10, 1, "1" to false, "2" to true, "3" to false)
        assertEquals(expect, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun 존재하지_않는_상품을_선택하면_예외가_발생한다() {
        // given
        val cart = makeCart(10, 1, "1" to false, "2" to false, "3" to false)

        // when
        val productToSelect = makeCheckableCartProduct(10, 1, "4" to false)
        cart.selectProduct(productToSelect, true)

        // then
    }

    @Test
    fun 장바구니에_2개의_상품이_체크되어_있으면_2를_반환한다() {
        // given
        val cart = makeCart(0, 0, "1" to true, "2" to true, "3" to false)

        // when
        val expect = cart.calculateCheckedProductsCount()

        // then
        val actual = 2
        assertEquals(expect, actual)
    }

    @Test
    fun 장바구니에_가격이_10이고_수량이_4인_상품이_2개_체크되어_있으면_80을_반환한다() {
        // given
        val cart = makeCart(10, 4, "1" to true, "2" to true, "3" to false)

        // when
        val expect = cart.calculateCheckedProductsPrice()

        // then
        val actual = 80
        assertEquals(expect, actual)
    }

    @Test
    fun 장바구니에_모든_상품이_체크되어_있으면_참을_반환한다() {
        // given
        val cart = makeCart(0, 0, "1" to true, "2" to true, "3" to true)

        // when
        val expect = cart.isTotalChecked()

        // then
        val actual = true
        assertEquals(expect, actual)
    }

    @Test
    fun 장바구니에_모든_상품이_체크되어_있지_않으면_거짓을_반환한다() {
        // given
        val cart = makeCart(0, 0, "1" to false, "2" to true, "3" to true)

        // when
        val expect = cart.isTotalChecked()

        // then
        val actual = false
        assertEquals(expect, actual)
    }

    private fun makeCart(price: Int, amount: Int, vararg product: Pair<String, Boolean>): Cart {
        return Cart(
            product.map {
                makeCheckableCartProduct(price, amount, it)
            }
        )
    }

    private fun makeCheckableCartProduct(
        price: Int,
        amount: Int,
        product: Pair<String, Boolean>
    ): CheckableCartProduct {
        return CheckableCartProduct(
            product.second,
            CartProduct(
                amount,
                Product(
                    URL(""), product.first, price
                )
            )
        )
    }
}
