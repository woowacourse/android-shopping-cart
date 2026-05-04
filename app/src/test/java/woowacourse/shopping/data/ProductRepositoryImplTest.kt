package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ProductRepositoryImplTest {
    @Test
    fun `특정 상품을 id를 통해 가져올 수 있다`() {
        val productManager = ProductRepositoryImpl()

        productManager.getProductById(id = "1").name shouldEqual "bolt"
    }

    @Test
    fun `존재하지 않는 id일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager = ProductRepositoryImpl()

            productManager.getProductById(id = "아아아아")
        }
    }

    @Test
    fun `상품을 모두 로드하면 hasNext는 false가 된다`() =
        runTest {
            val productRepository = ProductRepositoryImpl()
            repeat(2) {
                productRepository.getProducts()
            }
            productRepository.hasNext shouldEqual false
        }

    @Test
    fun `모든 상품을 로드한 후 추가로 조회하면 빈 리스트를 반환한다`() =
        runTest {
            val productRepository = ProductRepositoryImpl()

            repeat(2) {
                productRepository.getProducts()
            }

            val result = productRepository.getProducts()
            result.size shouldEqual 0
        }
}
