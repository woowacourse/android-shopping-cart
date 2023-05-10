package woowacourse.shopping.data.datasource.recentproduct

import woowacourse.shopping.data.model.DataProduct

interface RecentProductDataSource {
    interface Local {
        fun getPartially(size: Int): List<DataProduct>
        fun add(product: DataProduct)
    }

    interface Remote
}
