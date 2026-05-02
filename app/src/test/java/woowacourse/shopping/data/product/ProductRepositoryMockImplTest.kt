package woowacourse.shopping.data.product

import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.MockData

class ProductRepositoryMockImplTest {
    @Test
    fun `id가 일치하는 상품이 목록에 존재한다면 상품을 반환할 수 있다`() {
        val mockRepository = ProductRepositoryMockImpl()

        val result =
            runBlocking {
                mockRepository.getProduct("test")
            }

        assertTrue(MockData.products.contains(result))
    }

    @Test
    fun `첫 번째 페이지이며 한 페이지에 20개의 아이템이 보인다면 첫번째부터 스무번째까지 아이템을 리스트로 반환한다`() {
        val mockRepository = ProductRepositoryMockImpl()

        val result =
            runBlocking {
                mockRepository.getPagedProducts(page = 0, pageSize = 20)
            }

        result shouldBeEqual (MockData.products.subList(0, 20))
    }
}
