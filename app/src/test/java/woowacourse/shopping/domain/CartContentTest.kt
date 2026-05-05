package woowacourse.shopping.domain

import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartContentTest {
    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 같으면 true를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val product = normalProduct("임시", id = "1")

        val cartContent = CartContent(product, CartContentQuantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartContent.hasProductId(
            "1",
        )

        // then : true를 반환한다
        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 다르면 false를 반환한다`() {

        // given : 상품과 CartItem이 주어진다
        val product = normalProduct("임시", "1")
        val other = normalProduct("임시2", "2")

        val cartContent = CartContent(product, CartContentQuantity(1))

        // when : 다른 상품을 입력받아 비교할 때
        val result = cartContent.hasProductId(
            other.id,
        )

        // then : false를 반환한다
        assertEquals(false, result)
    }

    private fun normalProduct(
        title: String,
        id: String = UUID.randomUUID().toString(),
    ): Product = Product(
        id = id,
        name = title,
        price = Money(1000),
        imageUrl = "",
    )
}
