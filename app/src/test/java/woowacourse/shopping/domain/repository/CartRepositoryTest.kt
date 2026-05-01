package woowacourse.shopping.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName

class CartRepositoryTest {
    private val product =
        Product(
            id = "1",
            name = ProductName("상품"),
            price = Money(2000),
            imageUrl = "ds",
        )

    @Test
    fun `등록되지 않은 상품을 등록하면 NewAdded를 반환한다`() {
        val uniqueProduct =
            Product(
                name = ProductName("상품"),
                price = Money(2000),
                imageUrl = "ds",
            )
        assertThat(
            CartRepository.addItem(product = uniqueProduct),
        ).isInstanceOf(AddItemResult.NewAdded::class.java)
    }

    @Test
    fun `이미 등록된 상품을 다시 등록하면 DuplicateItem을 반환한다`() {
        CartRepository.addItem(product = product)
        assertThat(
            CartRepository.addItem(product = product),
        ).isEqualTo(AddItemResult.DuplicateItem)
    }
}
