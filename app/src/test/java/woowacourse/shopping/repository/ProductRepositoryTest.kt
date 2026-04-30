package woowacourse.shopping.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.Product

class ProductRepositoryTest {
    private val product = Product(1, "A", 1_000, "")
    private val productRepository: ProductRepository = MemoryProductRepository(products = listOf(product))

    @Test
    fun `A 상품이 저장되어 있는 레파지토리에서 모든 상품을 꺼내오면 A 상품이 조회된다`() {
        productRepository.getProducts().single() shouldBe product
    }

    @Test
    fun `id값이 2인 상품이 저장되어 있지 않은 레파지토리에서 id값이 2인 상품을 조회하면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            productRepository.getProduct(2)
        }
    }

    @Test
    fun `A 상품이 저장된 레파지토리에서 동일 상품의 id로 조회하면 A 상품이 조회된다`() {
        productRepository.getProduct(1) shouldBe product
    }
}
