package woowacourse.shopping.source

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.ProductDataSource
import kotlin.concurrent.thread

class FakeProductDataSource(
    private val pagingStrategy: NumberPagingStrategy<ProductData> = NumberPagingStrategy(20),
    private val allProducts: MutableList<ProductData>,
) : ProductDataSource {
    override fun findByPaged(page: Int): List<ProductData> = pagingStrategy.loadPagedData(page, allProducts)

    override fun findById(id: Long): ProductData =
        allProducts.find { it.id == id }
            ?: throw NoSuchElementException("there is no product with id: $id")

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, allProducts)

    override fun shutDown(): Boolean {
        println("shutDown")
        return true
    }

    // async function with callback
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
        return runCatching { findByPaged(page) }
    }

    override fun findByIdResult(id: Long): Result<ProductData> {
        return runCatching { findById(id) }
    }

    override fun isFinalPageResult(page: Int): Result<Boolean> {
        return runCatching { isFinalPage(page) }
    }

    override fun findByPagedAsyncResult(page: Int, callback: (Result<List<ProductData>>) -> Unit) {
        thread {
            callback(runCatching { findByPaged(page) })
        }
    }

    override fun findByIdAsyncResult(id: Long, callback: (Result<ProductData>) -> Unit) {
        thread {
            callback(runCatching { findById(id) })
        }
    }

    override fun isFinalPageAsyncResult(page: Int, callback: (Result<Boolean>) -> Unit) {
        thread {
            callback(runCatching { isFinalPage(page) })
        }
    }
}
