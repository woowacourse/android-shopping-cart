package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.ProductName

class ProductNameTest {
    @Test
    fun `상품 이름이 비어있을 경우 예외가 발생한다`() {
        val value = ""
        assertThrows(IllegalArgumentException::class.java) {
            ProductName(value)
        }
    }

    @Test
    fun `상품 이름이 공백일 경우 예외가 발생한다`() {
        val value = " "
        assertThrows(IllegalArgumentException::class.java) {
            ProductName(value)
        }
    }
}
