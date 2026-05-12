package woowacourse.shopping.domain

import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartContentTest {

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 같으면 true를 반환한다`() {
        val product = normalProduct("임시", id = "1")
        val cartContent = CartContent(product, 1)

        val result = cartContent.hasProductId("1")

        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 CartItem 의 상품 id 와 다르면 false를 반환한다`() {
        val product = normalProduct("임시", "1")
        val other = normalProduct("임시2", "2")
        val cartContent = CartContent(product, 1)

        val result = cartContent.hasProductId(other.id)

        assertEquals(false, result)
    }

    @Test
    fun `상품의 수량이 1개 미만이면 오류가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            CartContent(normalProduct(), 0)
        }
    }

    private fun normalProduct(
        name: String = "상품",
        id: String = UUID.randomUUID().toString(),
    ): Product = Product(
        id = id,
        name = name,
        price = Money(1000),
        imageUrl = "",
    )
}
