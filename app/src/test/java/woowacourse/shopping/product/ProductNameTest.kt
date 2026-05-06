package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.model.ProductName

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

    @Test
    fun `상품 이름이 존재할 경우 검증 값이 참이 된다`() {
        val value = "웃고있는 크롱"
        assertTrue(ProductName.isValueValid(value))
    }
}
