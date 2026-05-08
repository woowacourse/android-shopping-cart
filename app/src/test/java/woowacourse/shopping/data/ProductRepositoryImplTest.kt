package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.fixture.ShoppingFixture

class ProductRepositoryImplTest {
    @Test
    fun `특정 상품을 id를 통해 가져올 수 있다`() {
        val productManager = ProductRepositoryImpl(FakeProductDataSource())

        productManager.getProductById(id = "1").name shouldEqual "bolt"
    }

    @Test
    fun `존재하지 않는 id일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager = ProductRepositoryImpl(FakeProductDataSource())

            productManager.getProductById(id = "아아아아")
        }
    }

    @Test
    fun `상품 목록을 원하는 시작 인덱스와 크기로 가져올 수 있다`() =
        runTest {
            val productRepository = ProductRepositoryImpl(FakeProductDataSource())

            productRepository.getProducts(startIndex = 0, count = 1) shouldEqual listOf(ShoppingFixture.getProduct(id = "0"))
        }

    @Test
    fun `시작 인덱스가 범위를 벗어나면 예외가 발생한다`() =
        runTest {
            assertThrows<IllegalArgumentException> {
                val productRepository = ProductRepositoryImpl(FakeProductDataSource())

                productRepository.getProducts(startIndex = 99, count = 1)
            }
        }

    @Test
    fun `count가 남은 상품보다 많으면 남은 상품만 반환한다`() =
        runTest {
            val productRepository = ProductRepositoryImpl(FakeProductDataSource())

            productRepository.getProducts(startIndex = 0, count = 99).size shouldEqual 25
        }
}

class FakeProductDataSource : ProductDataSource {
    override val products: List<Product> = List(25) {
        ShoppingFixture.getProduct(id = "$it")
    }
}
