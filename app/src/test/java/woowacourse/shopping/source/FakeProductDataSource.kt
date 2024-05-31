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
