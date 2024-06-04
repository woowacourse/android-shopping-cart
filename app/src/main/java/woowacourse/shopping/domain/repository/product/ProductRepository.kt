package woowacourse.shopping.domain.repository.product

import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct

interface ProductRepository {
    fun fetchSinglePage(page: Int): List<CartableProduct>

    fun fetchProduct(id: Long): CartableProduct

    fun addProductHistory(productHistory: ProductHistory)

    fun fetchProductHistory(size: Int): List<RecentProduct>

    fun fetchLatestHistory(): RecentProduct?
}
