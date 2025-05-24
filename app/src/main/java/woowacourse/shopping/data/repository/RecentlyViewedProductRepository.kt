package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity

interface RecentlyViewedProductRepository {
    fun insertRecentlyViewedProductUid(uid: Int)

    fun getRecentlyViewedProducts(callback: (List<CartProductEntity>) -> Unit)
}
