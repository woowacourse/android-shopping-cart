package woowacourse.shopping.data.product

import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.entity.Product
import kotlin.concurrent.thread

class RoomProductRepository(private val productDao: ProductDao) : ProductRepository {
    init {
        thread {
            productDao.insertAll(dummyProducts)
        }
    }

    override fun find(id: Long): Product {
        var product: Product? = null
        thread {
            product = productDao.findOrNull(id)
        }.join()
        return product ?: throw IllegalArgumentException(INVALID_ID_MESSAGE)
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        var products: List<Product> = emptyList()
        thread {
            products = productDao.findRange(page, pageSize)
        }.join()
        return products
    }

    override fun totalProductCount(): Int {
        var totalProductCount = 0
        thread {
            totalProductCount = productDao.totalCount()
        }.join()
        return totalProductCount
    }

    companion object {
        private const val INVALID_ID_MESSAGE = "해당하는 id의 상품이 존재하지 않습니다."
    }
}
