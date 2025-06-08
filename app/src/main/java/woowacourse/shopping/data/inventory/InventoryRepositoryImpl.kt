package woowacourse.shopping.data.inventory

import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.data.toProduct
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class InventoryRepositoryImpl(private val productDao: ProductDao) : InventoryRepository {
    override fun getOrNull(
        id: Int,
        onResult: (Product?) -> Unit,
    ) {
        thread {
            val product = productDao.getOrNull(id)?.toProduct()
            onResult(product)
        }
    }

    override fun getAll(onSuccess: (List<Product>) -> Unit) {
        thread {
            val result = productDao.getAll().map(ProductEntity::toProduct)
            onSuccess(result)
        }
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<Product>) -> Unit,
    ) {
        getAll { allItems ->
            val from = (pageSize * pageIndex).coerceAtMost(allItems.size)
            val to = (from + pageSize).coerceAtMost(allItems.size)
            val items = allItems.subList(from, to)
            val hasPrevious = pageIndex > 0
            val hasNext = to < allItems.size
            val page =
                Page(
                    items,
                    hasPrevious,
                    hasNext,
                    pageIndex,
                )
            onSuccess(page)
        }
    }

    override fun insert(product: Product) {
        thread {
            productDao.insert(product.toEntity())
        }.join()
    }
}
