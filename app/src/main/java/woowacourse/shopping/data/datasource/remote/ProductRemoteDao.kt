package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.domain.Product

class ProductRemoteDao : ProductDataSource {
    override fun selectAll(start: Int, range: Int): List<Product> {
        TODO("Not yet implemented")
    }

    override fun initMockData() {
        TODO("Not yet implemented")
    }
}