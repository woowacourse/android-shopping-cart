package woowacourse.shopping.data.inventory

import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.product.toDomain
import woowacourse.shopping.data.product.toEntity
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class InventoryRepository2Impl(private val productDao: ProductDao) : InventoryRepository2 {
    override fun getAll(onSuccess: (List<Product>) -> Unit) {
        thread {
            val result = productDao.getAll().map(ProductEntity::toDomain)
            onSuccess(result)
        }
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
        onSuccess: (Page<Product>) -> Unit,
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

    override fun insert(product: Product) {
        productDao.insert(product.toEntity())
    }
}
