package woowacourse.shopping.data.datasource.recentproduct

import woowacourse.shopping.data.model.DataRecentProduct

interface RecentProductDataSource {
    interface Local {
        fun getPartially(size: Int): List<DataRecentProduct>
        fun add(product: DataRecentProduct)
    }

    interface Remote
}
