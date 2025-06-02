package woowacourse.shopping.data.local.repository

import woowacourse.shopping.data.local.dummy.ProductDummy
import woowacourse.shopping.domain.product.Product

class ProductRepository(private val productDao: ProductDummy) {
    fun getProductPagedItems(
        pageNumber: Int,
        loadSize: Int,
    ): List<Product> {
        return productDao.findPagedItems(pageNumber, loadSize)
    }

    fun fetchById(id: Long): Product {
        return productDao.findById(id) ?: throw IllegalArgumentException("")
    }

    fun size(): Int {
        return productDao.size()
    }
}
