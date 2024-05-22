package woowacourse.shopping.data.network.service

import woowacourse.shopping.data.network.model.ProductPageResponse
import woowacourse.shopping.data.network.model.ProductResponse
import java.util.concurrent.Future

interface ShoppingProductService {
    fun fetchProducts(currentPage: Int, size: Int): Future<ProductPageResponse>

    fun fetchProductById(id: Long): Future<ProductResponse>
}