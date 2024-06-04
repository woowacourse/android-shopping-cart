package study

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService
import kotlin.concurrent.thread

class RemoteProductDataSourceStudy(
    private val productApiService: ProductApiService = MockProductApiService(),
) : ProductDataSourceStudy {
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

    override fun findByPagedAsync(
        page: Int,
        callback: (List<ProductData>) -> Unit,
    ) {
        thread {
            callback(findByPaged(page))
        }
    }

    override fun findByIdAsync(
        id: Long,
        callback: (ProductData) -> Unit,
    ) {
        thread {
            callback(findById(id))
        }
    }

    override fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            callback(isFinalPage(page))
        }
    }

    override fun findByPagedResult(page: Int): Result<List<ProductData>> {
        return runCatching {
            productApiService.loadPaged(page)
        }
    }

    override fun findByIdResult(id: Long): Result<ProductData> {
        return runCatching {
            productApiService.loadById(id)
        }
    }

    override fun isFinalPageResult(page: Int): Result<Boolean> {
        return runCatching {
            val count = productApiService.count()
            page * 20 >= count
        }
    }

    override fun findByPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductData>>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    productApiService.loadPaged(page)
                },
            )
        }
    }

    override fun findByIdAsyncResult(
        id: Long,
        callback: (Result<ProductData>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    productApiService.loadById(id)
                },
            )
        }
    }

    override fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    val count = productApiService.count()
                    page * 20 >= count
                },
            )
        }
    }
}
