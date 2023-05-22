package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.util.WoowaResult

interface ProductRepository {

    fun getProduct(id: Long): WoowaResult<Product>
    fun getProducts(unit: Int, lastId: Long): List<ProductInCart>
    fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedProduct>
    fun getLastViewedProduct(): WoowaResult<RecentlyViewedProduct>
    fun addRecentlyViewedProduct(productId: Long): Long
    fun isLastProduct(id: Long): Boolean
}
