package woowacource.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Product

internal class ProductTest {


    @Test
    fun `상품의 아이디가 같으면 같다고 판단한다`() {
        val oneProduct = Product(1L, "imageUrl1", "name1", 10000)
        val otherProduct = Product(1L, "imageUrl2", "name2", 20000)

        assertThat(oneProduct).isEqualTo(otherProduct)
    }

    @Test
    fun `상품의 아이디가 다르면 다르다고 판단한다`() {
        val oneProduct = Product(1L, "imageUrl", "name", 10000)
        val otherProduct = Product(2L, "imageUrl", "name", 10000)

        assertThat(oneProduct).isNotEqualTo(otherProduct)
    }
}