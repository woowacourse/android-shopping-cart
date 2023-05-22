package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ShopTest {

    @Test
    fun 장바구니에_상품을_추가하면_상품이_담긴다() {
        // given
        val shop = Shop(emptyList())

        // when
        val product = makeCartProductMock()
        val actual = shop.add(product)

        // then
        val expected = Shop(listOf(makeCartProductMock()))
        assertEquals(expected, actual)
    }

    @Test
    fun 장바구니에서_상품을_삭제하면_상품이_사라진다() {
        // given
        val shop = Shop(listOf(makeCartProductMock()))

        // when
        val product = makeCartProductMock()
        val actual = shop.remove(product)

        // then
        val expected = Shop(emptyList())
        assertEquals(expected, actual)
    }
}
