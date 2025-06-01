package woowacourse.shopping.data.local.repository

import woowacourse.shopping.data.remote.ProductDao
import woowacourse.shopping.domain.product.Product

class ProductRepository(private val productDao: ProductDao) {
    fun getProductPagedItems(
        pageNumber: Int,
        loadSize: Int,
    ): List<Product> {
        val fromIndex = pageNumber * loadSize
        val toIndex = (fromIndex + loadSize).coerceAtMost(productDao.size())
        val products = productDao.findPagedItems(fromIndex, toIndex)
        return products.map { it.toDomain() }
    }

    fun fetchById(id: Long): Product {
        val product = productDao.findById(id) ?: throw IllegalArgumentException("")
        return product.toDomain()
    }

    fun size(): Int {
        val totalSize = productDao.size()
        return totalSize
    }
}
