package woowacourse.shopping.data.network

import woowacourse.shopping.data.network.entity.ProductEntity
import woowacourse.shopping.data.network.entity.ProductPageEntity

interface ProductService {
    fun getProduct(productId: Long): ProductEntity

    fun getProducts(productIds: List<Long>): List<ProductEntity>

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageEntity
}
