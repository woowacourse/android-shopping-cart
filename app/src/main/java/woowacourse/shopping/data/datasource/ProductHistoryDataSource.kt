package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct

interface ProductHistoryDataSource {
    fun addProductHistory(productHistory: ProductHistory)
    
    fun fetchProductHistory(size: Int): List<RecentProduct>
    
    fun fetchLatestHistory(): RecentProduct?
}
