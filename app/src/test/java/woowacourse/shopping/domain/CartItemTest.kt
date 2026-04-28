package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartItem(private val product: Product, private val quantity: Quantity) {
    fun isSame(other: Product): Boolean = product.isSame(other)
}

class CartItemTest {
    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 같으면 true를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val product = normalProduct("임시")

        val cartItem = CartItem(product, Quantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartItem.isSame(
            product,
        )

        // then : true를 반환한다
        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 다르면 false를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val product = normalProduct("임시")
        val other = normalProduct("임시2")

        val cartItem = CartItem(product, Quantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartItem.isSame(
            other,
        )

        // then : false를 반환한다
        assertEquals(false, result)
    }

    private fun normalProduct(title: String): Product = Product(
        name = title,
        price = Money(1000),
        imageUrl = "",
    )
}
