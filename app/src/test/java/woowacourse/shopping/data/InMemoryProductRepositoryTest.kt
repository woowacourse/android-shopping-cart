package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InMemoryProductRepositoryTest {
    private val products = ProductData.products
    private val size = products.size
    private val repository = InMemoryProductRepository(source = products)

    @Test
    fun `offset과 limit의 범위에 따라 리스트를 반환한다`() =
        runTest {
            val page = repository.getProducts(0, 20)
            Assertions.assertThat(page).isEqualTo(products.subList(0, 20))
        }

    @Test
    fun `마지막 페이지는 limit보다 작은 개수를 반환할 수 있다`() =
        runTest {
            val page = repository.getProducts(offset = size - 5, limit = 20)
            Assertions.assertThat(page.size).isEqualTo(5)
        }

    @Test
    fun `offset이 전체 크기보다 크거나 같으면 빈 리스트를 반환한다`() =
        runTest {
            Assertions.assertThat(repository.getProducts(offset = size, limit = 20)).isEmpty()
            Assertions.assertThat(repository.getProducts(offset = size + 5, limit = 20)).isEmpty()
        }
}
