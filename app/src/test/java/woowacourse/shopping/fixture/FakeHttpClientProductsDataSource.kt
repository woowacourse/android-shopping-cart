package woowacourse.shopping.fixture

import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.data.client.HttpClientProductDataSource

class FakeHttpClientProductsDataSource(
    mockWebServer: MockWebServer,
) : HttpClientProductDataSource() {
    override val findUrl: String = mockWebServer.url("/products").toString()
    override val totalSizeUrl: String = mockWebServer.url("/total-size").toString()
}
