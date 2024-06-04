package woowacourse.shopping.data.source

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService
import woowacourse.shopping.remote.source.RemoteProductDataSource

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
    fun `마지막 페이지인지 확인`() {
        // given
        // TODO:
        // when

        // then
    }
}
