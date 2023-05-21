package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.entity.ProductEntity

class ProductRemoteDao : ProductDataSource {
    override fun selectByRange(start: Int, range: Int): List<ProductEntity> {
        TODO("Not yet implemented")
    }

    override fun initMockData() {
        TODO("Not yet implemented")
    }
}
