package woowacourse.shopping.data

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.repository.RemoteProductRepository
import woowacourse.shopping.data.source.remote.ProductRemoteDataSource
import woowacourse.shopping.data.source.remote.mock.MockDispatcher
import woowacourse.shopping.data.source.remote.mock.ProductData
import woowacourse.shopping.data.source.remote.mock.ProductData.products

class RemoteProductRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var repository: RemoteProductRepository
    private val totalSize = Json.parseToJsonElement(ProductData.products).jsonArray.size

    @BeforeEach
    fun setUp() {
        server =
            MockWebServer().apply {
                dispatcher = MockDispatcher()
                start()
            }
        val dataSource =
            ProductRemoteDataSource(
                client = OkHttpClient(),
                baseUrlProvider = { server.url("/").toString() },
            )
        repository = RemoteProductRepository(dataSource)
    }

    @AfterEach
    fun tearDown() {
        server.close()
    }

    @Test
    fun `offset과 limit의 범위에 따라 리스트를 반환한다`() =
        runTest {
            val page = repository.getProducts(0, 20)
            assertThat(page).hasSize(20)
        }

    @Test
    fun `마지막 페이지는 limit보다 작은 개수를 반환할 수 있다`() =
        runTest {
            val page = repository.getProducts(offset = totalSize - 5, limit = 20)
            assertThat(page).hasSize(5)
        }

    @Test
    fun `offset이 전체 크기보다 크거나 같으면 빈 리스트를 반환한다`() =
        runTest {
            assertThat(repository.getProducts(offset = totalSize, limit = 20)).isEmpty()
            assertThat(repository.getProducts(offset = totalSize + 5, limit = 20)).isEmpty()
        }

    @Test
    fun `id로 상품을 단건 검색할 수 있다`() =
        runTest {
            val product = repository.getProductById(1L)
            assertThat(product.id).isEqualTo(1L)
        }
}
