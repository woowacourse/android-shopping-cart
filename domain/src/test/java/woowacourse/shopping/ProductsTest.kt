package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductsTest {
    @Test
    fun `상품들을 추가할 수 있다`() {
        // given
        val products = Products()
        val productItems = listOf(
            Product(1, "test.com", "햄버거", Price(10000)),
        )
        // when
        products.addProducts(productItems)

        // then
        assertThat(products.items).isEqualTo(productItems)
    }
}
