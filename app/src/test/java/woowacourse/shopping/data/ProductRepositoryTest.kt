package woowacourse.shopping.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.Product

class ProductRepositoryTest {

    private lateinit var productRepository: ProductRepository

    @Test
    fun `20개 이하일 때`() {
        productRepository = ProductRepository(
            products = List(5) { ProductFixture }
        )
        val firstPage = productRepository.getNextPage()
        assertEquals(firstPage.size, 5)
    }

    @Test
    fun `20개일 때`() {
        productRepository = ProductRepository(
            products = List(20) { ProductFixture }
        )
        val firstPage = productRepository.getNextPage()
        assertEquals(firstPage.size, 20)
    }

    @Test
    fun `20개 초과일 때`() {
        productRepository = ProductRepository(
            products = List(23) { ProductFixture }
        )
        val firstPage = productRepository.getNextPage()
        assertEquals(firstPage.size, 20)
    }

    @Test
    fun `다음 페이지 데이터를 불러온다`() {
        productRepository = ProductRepository(
            products = List(40) { ProductFixture.copy(name = "$it") }
        )

        val firstPage = productRepository.getNextPage()
        val secondPage = productRepository.getNextPage()

        assertThat(firstPage.size).isEqualTo(20)
        assertThat(secondPage.size).isEqualTo(20)
        assertThat(firstPage.intersect(secondPage.toSet())).isEmpty()
    }

    companion object {
        private val ProductFixture = Product(
            name = "Cleo Parker",
            price = 9912,
            imageUrl = "https://www.google.com/#q=dis"
        )
    }

}
