package woowacourse.shopping.data.product

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductResponse
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductRepositoryImpl : ProductRepository {
    private val productDao = ProductDao()

    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): ProductResponse {
        val pagingData = productDao.findPagingProducts(offset, pagingSize)
        val hasNext = offset + pagingSize < productDao.getProductCount()
        return ProductResponse(hasNext, pagingData)
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }
}
