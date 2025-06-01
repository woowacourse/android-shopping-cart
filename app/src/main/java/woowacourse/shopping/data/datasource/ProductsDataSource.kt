package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.network.ProductService
import woowacourse.shopping.data.network.entity.ProductEntity
import woowacourse.shopping.data.network.entity.ProductPageEntity

class ProductsDataSource(private val service: ProductService) {
    fun getProduct(productId: Long): ProductEntity = service.getProduct(productId)

    fun getProducts(productIds: List<Long>): List<ProductEntity> {
        return service.getProducts(productIds)
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageEntity = service.singlePage(fromIndex, toIndex)
}
