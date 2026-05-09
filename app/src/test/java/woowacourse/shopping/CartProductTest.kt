package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

class CartProductTest {
    @Test
    fun `개수는 0 이상이라면 예외가 발생한다`() {
        assertThrows <IllegalArgumentException> {
            CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = -1)
        }
    }

    @Test
    fun `개수가 0 이상이라면 예외가 발생하지 않는다`() {
        assertDoesNotThrow {
            CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 0)
        }
    }
}
