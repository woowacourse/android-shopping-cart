package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.product.MockProductService
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.service.ProductService
import woowacourse.shopping.utils.exception.NoSuchDataException
import kotlin.concurrent.thread

class ProductRepositoryImpl : ProductRepository {
    private val mockProductService: ProductService = MockProductService()

    override fun loadPagingProducts(offset: Int): List<Product> {
        var pagingData: List<Product> = listOf()
        thread {
            pagingData = mockProductService.findPagingProducts(offset, PRODUCT_LOAD_PAGING_SIZE)
        }.join()
        if (pagingData.isEmpty()) throw NoSuchDataException()
        return pagingData
    }

    override fun getProduct(productId: Long): Product {
        var product: Product? = null
        thread {
            product = mockProductService.findProductById(productId)
        }.join()
        return product ?: throw NoSuchDataException()
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
        const val DEFAULT_ITEM_SIZE = 0
    }
}
