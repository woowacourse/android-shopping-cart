package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
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
}

class FakeProductDataSource : ProductDataSource {
    override val products: List<Product> = listOf(ShoppingFixture.getProduct(id = "1"))
}
