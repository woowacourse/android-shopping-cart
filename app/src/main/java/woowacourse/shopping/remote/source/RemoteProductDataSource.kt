package woowacourse.shopping.remote.source

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService
import kotlin.concurrent.thread

class RemoteProductDataSource(
    private val productApiService: ProductApiService = MockProductApiService(),
) : ProductDataSource {
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

    override fun findAllUntilPageAsyncResult(
        page: Int,
        callback: (Result<List<ProductData>>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    val productsData = mutableListOf<ProductData>()
                    for (i in 1..page) {
                        productsData.addAll(productApiService.loadPaged(i))
                    }
                    productsData.toList()
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
