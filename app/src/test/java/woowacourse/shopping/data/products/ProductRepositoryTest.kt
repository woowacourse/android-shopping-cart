package woowacourse.shopping.data.products

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.PRODUCTS_IN_RANGE_0_2

class ProductRepositoryTest {
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        productRepository = ProductRepositoryImpl()
    }

    @Test
    fun `지정한 범위의 상품 데이터를 가져올 수 있다`() {
        val offset = 0
        val limit = 3
        val result = productRepository.fetchProducts(offset, limit)

        result shouldBe PRODUCTS_IN_RANGE_0_2
    }
}
