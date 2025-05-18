package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductTest {
    private lateinit var product: Product
    private val name: Name by lazy { Name("coffee") }
    private val price: Price by lazy { Price(100) }

    @BeforeEach
    fun setUp() {
        product = Product(0, name, price, "aa@aa.com")
    }

    @Test
    fun `상품은 id를 가진다`() {
        assertThat(product.id).isEqualTo(0)
    }

    @Test
    fun `상품은 이름을 가진다`() {
        assertThat(product.name).isEqualTo(name)
    }

    @Test
    fun `상품은 가격을 가진다`() {
        assertThat(product.price).isEqualTo(price)
    }

    @Test
    fun `상품은 이미지Url을 가진다`() {
        assertThat(product.imageUrl).isEqualTo("aa@aa.com")
    }
}
