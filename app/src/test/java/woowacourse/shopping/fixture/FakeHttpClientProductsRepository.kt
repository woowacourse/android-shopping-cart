package woowacourse.shopping.fixture

import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.repository.HttpClientProductRepository

class FakeHttpClientProductsRepository(
    mockWebServer: MockWebServer,
) : HttpClientProductRepository() {
    override val findUrl: String = mockWebServer.url("/products").toString()
    override val totalSizeUrl: String = mockWebServer.url("/total-size").toString()
}
