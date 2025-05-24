package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.network.ProductService
import woowacourse.shopping.data.network.dto.ProductDto
import woowacourse.shopping.data.network.dto.ProductPageDto

class ProductsDataSource(private val service: ProductService) {
    fun getProduct(productId: Long): ProductDto = service.getProduct(productId)

    fun getProducts(productIds: List<Long>): List<ProductDto> {
        return productIds.map { service.getProduct(it) }
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageDto = service.singlePage(fromIndex, toIndex)
}
