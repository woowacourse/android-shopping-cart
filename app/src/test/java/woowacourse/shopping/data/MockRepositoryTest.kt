package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MockRepositoryTest {
    private val products = ProductData.products
    private val size = products.size
    private val repository = MockRepository(source = products)

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

    @Test
    fun `존재하지 않는 id로 상품을 조회하면 예외가 발생한다`() =
        runTest {
            val exception =
                try {
                    repository.getProductById("1111")
                    null
                } catch (e: IllegalArgumentException) {
                    e
                }

            assertThat(exception?.message).contains("존재하지 않는 상품입니다.")
        }
}
