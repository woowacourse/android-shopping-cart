@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductRepositoryTest {
    private lateinit var repo: ProductRepository

    @BeforeEach
    fun setUp() {
        repo = FakeProductRepository(ProductRepositoryFixture.products)
    }

    @Test
    fun `특정 오프셋과 사이즈(20개)를 요청했을 때, 정확히 해당 구간의 데이터를 반환한다`() =
        runBlocking {
            val size = 20
            val offset = 0

            val actual = repo.getProducts(offset, size).count()
            val expected = 20

            assertEquals(expected, actual)
        }

    @Test
    fun `전체 데이터 개수보다 큰 범위(남은 데이터가 20개 미만)를 요청했을 때, 예외 없이 남은 개수만큼만 정상 반환한다`() =
        runBlocking {
            val totalSize = repo.size
            val moreThanTotalSize = totalSize + 1

            val actual = repo.getProducts(0, moreThanTotalSize).count()
            val expected = repo.size

            assertEquals(expected, actual)
        }
}
