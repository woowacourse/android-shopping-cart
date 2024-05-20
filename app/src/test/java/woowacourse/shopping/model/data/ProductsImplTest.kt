package woowacourse.shopping.model.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.shopping.model.Product

class ProductsImplTest {
    @BeforeEach
    fun setUp() {
        ProductsImpl.deleteAll()
    }

    @Test
    fun `상품이 저장되어야 한다`() {
        // given
        // when
        val id = ProductsImpl.save(product)
        val actual = ProductsImpl.find(id)

        assertThat(actual).isEqualTo(product.copy(id = id))
    }

    @Test
    fun `상품을 찾을 수 있다`() {
        // given
        val id = ProductsImpl.save(product.copy(name = "맥북 프로"))
        // when
        val actual = ProductsImpl.find(id)
        // then
        assertThat(actual.name).isEqualTo("맥북 프로")
    }

    @Test
    fun `모든 상품을 찾을 수 있다`() {
        // given
        ProductsImpl.save(product.copy(name = "갤럭시북"))
        ProductsImpl.save(product.copy(name = "맥북"))
        ProductsImpl.save(product.copy(name = "그램"))

        // when
        val actual = ProductsImpl.findAll()

        // then
        assertAll(
            "모든 상품을 확인",
            { assertThat(actual[0].name).isEqualTo("갤럭시북") },
            { assertThat(actual[1].name).isEqualTo("맥북") },
            { assertThat(actual[2].name).isEqualTo("그램") },
        )
    }

    @Test
    fun `모두 지울 수 있다`() {
        // given
        ProductsImpl.save(product.copy(name = "갤럭시북"))
        ProductsImpl.save(product.copy(name = "맥북"))
        ProductsImpl.save(product.copy(name = "그램"))

        // when
        ProductsImpl.deleteAll()
        val actual = ProductsImpl.findAll()

        // then
        assertThat(actual).isEmpty()
    }

    companion object {
        private val product = Product(imageUrl = "", name = "맥북", price = 100)
    }
}
