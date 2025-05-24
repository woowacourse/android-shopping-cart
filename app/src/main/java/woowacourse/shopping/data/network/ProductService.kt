package woowacourse.shopping.data.network

import woowacourse.shopping.data.network.dto.ProductDto
import woowacourse.shopping.data.network.dto.ProductPageDto

interface ProductService {
    fun getProduct(productId: Long): ProductDto

    fun getProducts(productIds: List<Long>): List<ProductDto>

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ): ProductPageDto
}
