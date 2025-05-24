package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.network.ProductService
import woowacourse.shopping.data.network.entitiy.ProductEntity
import woowacourse.shopping.data.network.entitiy.ProductPageEntity

class ProductsDataSource(private val service: ProductService) {
    fun getProduct(productId: Long): ProductEntity = service.getProduct(productId)

    fun getProducts(productIds: List<Long>): List<ProductEntity> {
        return productIds.map { service.getProduct(it) }
    }

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageEntity = service.singlePage(fromIndex, toIndex)
}
