package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

class ProductTest {
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        product = Product(0, "coffee", Price(100), "aa@aa.com")
    }

    @Test
    fun `상품은 id를 가진다`() {
        assertThat(product.productId).isEqualTo(0)
    }

    @Test
    fun `상품은 이름을 가진다`() {
        assertThat(product.name).isEqualTo("coffee")
    }

    @Test
    fun `상품은 가격을 가진다`() {
        assertThat(product.price).isEqualTo(100)
    }

    @Test
    fun `상품은 이미지Url을 가진다`() {
        assertThat(product.imageUrl).isEqualTo("aa@aa.com")
    }
}
