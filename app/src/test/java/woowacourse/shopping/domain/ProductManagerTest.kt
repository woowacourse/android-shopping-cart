package woowacourse.shopping.domain

import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.fixture.ShoppingFixture

class ProductManagerTest {
    @Test
    fun `판매하는 상품 리스트를 가진다`() {
        assertDoesNotThrow {
            ProductManager(
                products = listOf(ShoppingFixture.getProduct()),
            )
        }
    }

    @Test
    fun `특정 상품을 id를 통해 가져올 수 있다`() {
        val product = ShoppingFixture.getProduct(id = "1")
        val productManager =
            ProductManager(
                products = listOf(product),
            )

        productManager.getProductById(id = "1") shouldEqual product
    }

    @Test
    fun `존재하지 않는 id일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val product = ShoppingFixture.getProduct(id = "1")
            val productManager =
                ProductManager(
                    products = listOf(product),
                )

            productManager.getProductById(id = "아아아아")
        }
    }
}
