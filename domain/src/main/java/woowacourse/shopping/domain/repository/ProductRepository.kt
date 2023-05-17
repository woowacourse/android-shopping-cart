package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.util.WoowaResult

interface ProductRepository {

    fun getProduct(id: Long): WoowaResult<Product>
    fun getProducts(unit: Int, lastId: Long): List<Product>
    fun getRecentlyViewedProducts(unit: Int): List<Product>
    fun addRecentlyViewedProduct(productId: Long): Long
    fun isLastProduct(id: Long): Boolean
}
