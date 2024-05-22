package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.product.ProductDao
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import kotlin.concurrent.thread

class ProductRepositoryImpl : ProductRepository {
    private val productDao = ProductDao()

    override fun loadPagingProducts(offset: Int): List<Product> {
        var pagingData: List<Product> = listOf()
        thread {
            pagingData = productDao.findPagingProducts(offset, PRODUCT_LOAD_PAGING_SIZE)
        }.join()
        if (pagingData.isEmpty()) throw NoSuchDataException()
        return pagingData
    }

    override fun getProduct(productId: Long): Product {
        var product: Product?= null
        thread {
            product = productDao.findProductById(productId)
        }.join()
        return product ?: throw NoSuchDataException()
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
        const val DEFAULT_ITEM_SIZE = 0
    }
}
