package woowacourse.shopping.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDataSourceTest {
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
    }

    @Test
    fun `상품의 id를 전달받으면 id에 해당하는 삼품을 전달한다`() {
        // when
        val id = 1L

        // given
        val result = productRepository.getById(id)

        assertEquals(
            result,
            Product(1L, "맥북", Price(1000), ""),
        )
    }
}
