package woowacourse.shopping.data.network

import woowacourse.shopping.data.network.entitiy.ProductEntity
import woowacourse.shopping.data.network.entitiy.ProductPageEntity

interface ProductService {
    fun getProduct(productId: Long): ProductEntity

    fun getProducts(productIds: List<Long>): List<ProductEntity>

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageEntity
}
