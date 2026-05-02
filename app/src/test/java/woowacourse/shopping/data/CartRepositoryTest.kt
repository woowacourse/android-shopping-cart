package woowacourse.shopping.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

class CartRepositoryTest {
    private val product =
        Product(
            id = "1",
            name = ProductName("상품"),
            price = Money(2000),
            imageUrl = "ds",
        )

    @Test
    fun `등록되지 않은 상품을 등록하면 true를 반환한다`() {
        assertThat(
            CartRepository.addItem(product = product),
        ).isEqualTo(true)
    }

    @Test
    fun `이미 등록된 상품을 다시 등록하면 false를 반환한다`() {
        CartRepository.addItem(product = product)
        assertThat(
            CartRepository.addItem(product = product),
        ).isEqualTo(false)
    }
}
