package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    private lateinit var product1: Product
    private lateinit var product2: Product

    @BeforeEach
    fun setUp() {
        product1 = Product(0, "", "아메리카노", 2500)
        product2 = Product(0, "", "롱블랙", 2300)
    }

    @Test
    fun `장바구니는 모든 상품의 가격을 받아올 수 있다`() {
        // given
        val shoppingCart = ShoppingCart(listOf(product1, product2))
        // when
        val actual = shoppingCart.allPrice
        val expected = 4800
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니는 상품을 추가할 수 있다`() {
        // given
        val shoppingCart = ShoppingCart(listOf())
        shoppingCart.addProduct(product1)
        // when
        val actual = shoppingCart.products
        val expected = listOf(product1)
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니는 상품을 제거할 수 있다`() {
        // given
        val shoppingCart = ShoppingCart(listOf(product1))
        shoppingCart.removeProduct(product1)
        // when
        val actual = shoppingCart.products
        val expected = listOf<Product>()
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
