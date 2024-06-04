package woowacourse.shopping.source

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.ProductDataSource
import kotlin.concurrent.thread

class FakeProductDataSource(
    private val pagingStrategy: NumberPagingStrategy<ProductData> = NumberPagingStrategy(20),
    private val allProducts: MutableList<ProductData>,
) : ProductDataSource {
    override fun findByPagedAsyncResult(page: Int, callback: (Result<List<ProductData>>) -> Unit) {
        thread {
            callback(runCatching {
                pagingStrategy.loadPagedData(page, allProducts)
            })
        }
    }

    override fun findAllUntilPageAsyncResult(page: Int, callback: (Result<List<ProductData>>) -> Unit) {
        thread {
            callback(runCatching {
                val productsData = mutableListOf<ProductData>()
                for (i in 1..page) {
                    productsData.addAll(pagingStrategy.loadPagedData(i, allProducts))
                }
                productsData.toList()
            })
        }
    }

    override fun findByIdAsyncResult(id: Long, callback: (Result<ProductData>) -> Unit) {
        thread {
            callback(runCatching {
                allProducts.find { it.id == id }
                    ?: throw NoSuchElementException("there is no product with id: $id")
            })
        }
    }

    override fun isFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        thread {
            callback(runCatching {
                pagingStrategy.isFinalPage(page, allProducts)
            })
        }
    }
}
