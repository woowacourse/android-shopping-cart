package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService
import kotlin.concurrent.thread

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

    override fun findByPagedAsync(page: Int, callback: (List<ProductData>) -> Unit) {
        thread {
            callback(findByPaged(page))
        }
    }

    override fun findByIdAsync(id: Long, callback: (ProductData) -> Unit) {
        thread {
            callback(findById(id))
        }
    }

    override fun isFinalPageAsync(page: Int, callback: (Boolean) -> Unit) {
        thread {
            callback(isFinalPage(page))
        }
    }
}
