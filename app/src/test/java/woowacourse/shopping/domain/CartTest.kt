package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `상품을 추가하면 추가된 Cart 를 반환한다`() {
        // given : 상품과 Cart가 주어진다
        val product = normalProduct("임시")
        val cartContent = CartContent(product, CartContentQuantity(1))
        val cart = Cart(emptyList())

        // when : 상품을 Cart에 추가하면
        val newCart = cart.plusCartContent(cartContent)

        // then : 상품이 추가된 Cart가 반환된다
        val result = newCart.contains(product)
        assertEquals(result, true)
    }

    @Test
    fun `product가 존재한다면 true를 반환한다`() {
        // given : 상품과 Cart가 주어진다
        val product = normalProduct("임시")
        val cartContent = CartContent(product, CartContentQuantity(1))
        val cart = Cart(listOf(cartContent))

        // when : 상품이 존재하는지 확인할 때
        val result = cart.contains(product)

        // then : true가 반환된다
        assertEquals(result, true)
    }

    @Test
    fun `product가 존재하지 않는다면 false를 반환한다`() {
        // given : 상품과 Cart, 다른 상품이 주어진다
        val product = normalProduct("임시")
        val cartContent = CartContent(product, CartContentQuantity(1))
        val cart = Cart(listOf(cartContent))

        val otherProduct = normalProduct("임시2")

        // when : 상품이 존재하는지 확인할 때
        val result = cart.contains(otherProduct)

        // then : false가 반환된다
        assertEquals(result, false)
    }

    private fun normalProduct(title: String): Product = Product(
        name = title,
        price = Money(1000),
        imageUrl = "",
    )
}
