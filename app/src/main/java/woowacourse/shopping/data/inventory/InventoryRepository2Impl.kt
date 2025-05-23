package woowacourse.shopping.data.inventory

import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.data.product.toDomain
import woowacourse.shopping.data.product.toEntity
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

class InventoryRepository2Impl(private val productDao: ProductDao) : InventoryRepository2 {
    override fun getAll(): List<Product> {
        return productDao.getAll().map(ProductEntity::toDomain)
    }

    override fun getPage(
        pageSize: Int,
        pageIndex: Int,
    ): Page<Product> {
        val allItems = getAll()
        val from = pageSize * pageIndex
        val to = (from + pageSize).coerceAtMost(getAll().size)
        val items = allItems.subList(from, to)
        val hasPrevious = pageIndex > 0
        val hasNext = to < getAll().size
        return Page(
            items,
            hasPrevious,
            hasNext,
            pageIndex,
        )
    }

    override fun insert(product: Product) {
        productDao.insert(product.toEntity())
    }
}
