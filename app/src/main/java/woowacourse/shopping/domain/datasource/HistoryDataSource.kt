package woowacourse.shopping.domain.datasource

import woowacourse.shopping.domain.model.ProductBrowsingHistory

interface HistoryDataSource {
    fun putHistory(productBrowsingHistory: ProductBrowsingHistory)

    fun getHistories(size: Int): List<ProductBrowsingHistory>
}
