package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartProductsTest {
    @Test
    fun `상품들을 추가할 수 있다`() {
        // given
        val cartProducts = CartProducts()
        val productItems = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
        )

        // when
        cartProducts.addProducts(productItems)

        // then
        assertThat(cartProducts.items).isEqualTo(productItems)
    }

    @Test
    fun `2번 상품이 2개일 때 상품의 개수를 하나 줄이면 1개가 된다`() {
        // given
        val productItems = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 2, true),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.subProductByCount(
            product = Product(2, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 1, true),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }

    @Test
    fun `1번 상품이 1개일 때 상품의 개수를 줄일 수 없다`() {
        // given
        val productItems = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 2, true),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.subProductByCount(
            product = Product(1L, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 2, true),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }

    @Test
    fun `2번 상품이 2개일 때 상품의 개수를 하나 추가하면 3개가 된다`() {
        // given
        val productItems = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 2, true),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.addProductByCount(
            product = Product(2L, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(1L, Product(1L, "test.com", "햄버거", Price(10000)), 1, true),
            CartProduct(2L, Product(2L, "test.com", "햄버거", Price(10000)), 3, true),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }
}
