package woowacourse.shopping.core.repository

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.core.data.ProductData

class InMemoryProductRepositoryTest {
    private val products = ProductData.products
    private val size = products.size
    private val repository = InMemoryProductRepository(source = products)

    @Test
    fun `offset과 limit의 범위에 따라 리스트를 반환한다`() =
        runTest {
            val page = repository.getProducts(0, 20)
            assertThat(page).isEqualTo(products.subList(0, 20))
        }

    @Test
    fun `마지막 페이지는 limit보다 작은 개수를 반환할 수 있다`() =
        runTest {
            val page = repository.getProducts(offset = size - 5, limit = 20)
            assertThat(page.size).isEqualTo(5)
        }

    @Test
    fun `offset이 전체 크기보다 크거나 같으면 빈 리스트를 반환한다`() =
        runTest {
            assertThat(repository.getProducts(offset = size, limit = 20)).isEmpty()
            assertThat(repository.getProducts(offset = size + 5, limit = 20)).isEmpty()
        }
}
