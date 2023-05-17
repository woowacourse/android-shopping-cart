package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartProductsTest {
    @Test
    fun `상품들을 추가할 수 있다`() {
        // given
        val cartProducts = CartProducts()
        val productItems = listOf(
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
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
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 2),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.subProductByCount(
            product = Product(2, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 1),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }

    @Test
    fun `1번 상품이 1개일 때 상품의 개수를 하나 줄이면 삭제된다`() {
        // given
        val productItems = listOf(
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 2),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.subProductByCount(
            product = Product(1, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 2),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }

    @Test
    fun `2번 상품이 2개일 때 상품의 개수를 하나 추가하면 3개가 된다`() {
        // given
        val productItems = listOf(
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 2),
        )
        val cartProducts = CartProducts(productItems)

        // when
        cartProducts.addProductByCount(
            product = Product(2, "test.com", "햄버거", Price(10000)),
            count = 1,
        )

        // then
        val expected = listOf(
            CartProduct(Product(1, "test.com", "햄버거", Price(10000)), 1),
            CartProduct(Product(2, "test.com", "햄버거", Price(10000)), 3),
        )
        assertThat(cartProducts.items).isEqualTo(expected)
    }
}
