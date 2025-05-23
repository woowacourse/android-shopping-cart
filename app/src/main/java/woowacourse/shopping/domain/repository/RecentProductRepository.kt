package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.RecentlyViewedProduct
import woowacourse.shopping.domain.model.Product

interface RecentProductRepository {
    fun getRecentProducts(onResult: (Result<List<Product>>) -> Unit)

    fun insertRecentProduct(
        product: RecentlyViewedProduct,
        onResult: (Result<Unit>) -> Unit,
    )
}
