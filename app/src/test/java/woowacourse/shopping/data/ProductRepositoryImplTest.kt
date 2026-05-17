package woowacourse.shopping.data

import io.kotest.matchers.equals.shouldEqual
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.remote.model.ProductResponse
import woowacourse.shopping.fixture.ShoppingFixture

class ProductRepositoryImplTest {
    private val fakeDataSource =
        FakeProductDataSource().apply {
            items = List(25) { ShoppingFixture.getProductResponse(id = it.toString(), name = if (it == 1) "bolt" else "상품$it") }
        }
    private val repository = ProductRepositoryImpl(fakeDataSource)

    @Test
    fun `특정 상품을 id를 통해 가져올 수 있다`() =
        runTest {
            repository.getProductById(id = "1").name shouldEqual "bolt"
        }

    @Test
    fun `존재하지 않는 id일 경우 예외가 발생한다`() =
        runTest {
            assertThrows<NoSuchElementException> {
                repository.getProductById(id = "아아아아")
            }
        }

    @Test
    fun `상품 목록을 원하는 시작 인덱스와 크기로 가져올 수 있다`() =
        runTest {
            repository.getProducts(startIndex = 0, count = 1) shouldEqual listOf(ShoppingFixture.getProduct(id = "0", name = "상품0"))
        }

    @Test
    fun `count가 남은 상품보다 많으면 남은 상품만 반환한다`() =
        runTest {
            repository.getProducts(startIndex = 0, count = 99).size shouldEqual 25
        }
}

class FakeProductDataSource : ProductDataSource {
    var items = listOf<ProductResponse>()

    override suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<ProductResponse> {
        val endIndex = minOf(startIndex + count, items.size)
        return items.subList(startIndex, endIndex)
    }

    override suspend fun getProductById(id: String): ProductResponse = items.first { it.id == id }

    override suspend fun getTotalCount(): Int = items.size
}
