package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductRepositoryImplTest {
    @Test
    fun `특정 상품을 id를 통해 가져올 수 있다`() {
        val productManager =
            ProductRepositoryImpl()

        productManager.getProductById(id = "1").name shouldEqual "bolt"
    }

    @Test
    fun `존재하지 않는 id일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager =
                ProductRepositoryImpl()

            productManager.getProductById(id = "아아아아")
        }
    }
}
