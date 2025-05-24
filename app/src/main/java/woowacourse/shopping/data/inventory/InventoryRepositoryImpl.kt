package woowacourse.shopping.data.inventory

import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.Page
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import kotlin.concurrent.thread

class InventoryRepositoryImpl(private val productDao: ProductDao) : InventoryRepository {
    override fun getOrNull(
        id: Int,
        onResult: (InventoryProduct?) -> Unit,
    ) {
        thread {
            val product = productDao.getOrNull(id)?.toDomain()
            onResult(product)
        }
    }

    override fun getAll(onSuccess: (List<InventoryProduct>) -> Unit) {
        thread {
            val result = productDao.getAll().map(ProductEntity::toDomain)
            onSuccess(result)
        }
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<InventoryProduct>) -> Unit,
    ) {
        getAll { allItems ->
            val from = pageSize * pageIndex
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

    override fun insert(product: InventoryProduct) {
        productDao.insert(product.toEntity())
    }
}
