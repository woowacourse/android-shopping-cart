package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.fixture.FakeHttpClientProductsRepository
import woowacourse.shopping.fixture.MockWebServerBuilder
import woowacourse.shopping.fixture.TestProducts

class HttpProductsRepositoryTest {
    private val mockWebServer = MockWebServerBuilder.build()
    private val repository = FakeHttpClientProductsRepository(mockWebServer)

    @Test
    fun `상품 정보를 가져올 수 있다`() {
        val expected = TestProducts.deserialized

        val actual =
            repository.findAll(
                PageRequest(
                    20,
                    1,
                ),
            )
        assertThat(expected).containsAll(actual.items)
    }

    @Test
    fun `전체 사이즈를 가져올 수 있다`() {
        val expected = TestProducts.deserialized.size
        val actual = repository.totalSize()
        assertThat(expected).isEqualTo(actual)
    }
}
