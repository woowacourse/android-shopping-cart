package woowacourse.shopping

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.Product

class ProductTest {
    @Test
    fun `image URI는 빈 값이 될 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Product(
                imageUri = "",
                name = "name",
                price = 10000,
            )
        }
    }

    @Test
    fun `이름은 빈 값이 될 수 없다`() {
        assertThrows<IllegalArgumentException> { Product(
            imageUri = "testUri",
            name = "",
            price = 10000,
        ) }
    }

    @Test
    fun `가격은 0 초과이어야 한다`() {
        assertThrows<IllegalArgumentException> { Product(
            imageUri = "testUri",
            name = "name",
            price = 0,
        ) }
    }
}
