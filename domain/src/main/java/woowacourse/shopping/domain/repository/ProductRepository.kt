package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.util.WoowaResult

interface ProductRepository {

    fun getProduct(id: Long): WoowaResult<Product>
    fun getProductsFromLocal(unit: Int, lastIndex: Int): List<Product>
    fun getProductsFromRemote(unit: Int, lastIndex: Int): WoowaResult<List<Product>>
    fun getRecentlyViewedProducts(unit: Int): List<Product>
    fun addRecentlyViewedProduct(productId: Long, unit: Int): Long
    fun getLastViewedProduct(): WoowaResult<Product>
}
