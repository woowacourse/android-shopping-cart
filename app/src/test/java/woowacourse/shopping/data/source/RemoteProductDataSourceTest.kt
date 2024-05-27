package woowacourse.shopping.data.source

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.mockProductTestFixture
import woowacourse.shopping.mockProductsTestFixture
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService

class RemoteProductDataSourceTest {
    private lateinit var api: ProductApiService
    private lateinit var source: ProductDataSource

    @BeforeEach
    fun setUp() {
        api = MockProductApiService()
        source = RemoteProductDataSource(api)
    }

    @AfterEach
    fun tearDown() {
        api.shutDown()
    }

    @Test
    fun `페이징된 데이터 로드`() {
        // given
        // when
        val pagedData = source.findByPaged(1)

        // then
        assertThat(pagedData).isEqualTo(
            mockProductsTestFixture(20) {
                mockProductTestFixture((it + 1).toLong())
            },
        )
    }

    @Test
    fun `id 값으로 데이터 로드`() {
        // given
        // when
        val data = source.findById(1L)

        // then
        assertThat(data).isEqualTo(
            mockProductTestFixture(1),
        )
    }

    @Test
    fun `마지막 페이지인지 확인`() {
        // given
        val isFinalPage = source.isFinalPage(1)

        // when

        // then
    }
}
