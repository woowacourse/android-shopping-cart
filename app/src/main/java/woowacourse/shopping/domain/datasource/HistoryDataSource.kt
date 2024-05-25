package woowacourse.shopping.domain.datasource

import woowacourse.shopping.domain.model.History

interface HistoryDataSource {
    fun putHistory(history: History)

    fun getHistories(size: Int): List<History>
}
