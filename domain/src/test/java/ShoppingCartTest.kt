import org.junit.Assert.assertEquals
import org.junit.Test

class ShoppingCartTest {

    @Test
    fun `장바구니 상품의 개수를 증가시키면 개수가 하나 늘어난 장바구니 상품이 반환된다`() {
        // given
        val shoppingCartProduct = ShoppingCartProduct(count = 2)

        // when
        val actual = shoppingCartProduct.plusCount()

        // then
        val expected = ShoppingCartProduct(count = 3)

        assertEquals(expected, actual)
    }

    @Test
    fun `장바구니 상품의 개수를 감소시키면 개수가 하나 감소한 장바구니 상품이 반환된다`() {
        // given
        val shoppingCartProduct = ShoppingCartProduct(count = 2)

        // when
        val actual = shoppingCartProduct.minusCount()

        // then
        val expected = ShoppingCartProduct(count = 1)

        assertEquals(expected, actual)
    }
}
