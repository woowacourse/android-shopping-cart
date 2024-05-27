package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService

class RemoteProductDataSource(
    private val productApiService: ProductApiService = MockProductApiService(),
) : ProductDataSource {
    override fun findByPaged(page: Int): List<ProductData> {
        return productApiService.loadPaged(page)
    }

    override fun findById(id: Long): ProductData {
        return productApiService.loadById(id)
    }

    override fun isFinalPage(page: Int): Boolean {
        val count = productApiService.count()
        return page * 20 >= count
    }

    override fun shutDown(): Boolean {
        return productApiService.shutDown()
    }
}
