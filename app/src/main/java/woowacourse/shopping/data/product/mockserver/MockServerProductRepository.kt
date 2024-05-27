package woowacourse.shopping.data.product.mockserver

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductResponse
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class MockServerProductRepository(private val productService: MockProductService) :
    ProductRepository {
    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): ProductResponse {
        var pagingData: List<Product> = emptyList()
        var hasNext = false
        thread {
            pagingData = productService.requestProducts(offset, pagingSize)
            hasNext = offset + pagingSize < productService.requestProductCount()
        }.join()
        return ProductResponse(hasNext, pagingData)
    }

    override fun getProduct(productId: Long): Product {
        var product = Product.INVALID_PRODUCT
        thread { product = productService.requestProduct(productId) }.join()
        return product
    }
}
