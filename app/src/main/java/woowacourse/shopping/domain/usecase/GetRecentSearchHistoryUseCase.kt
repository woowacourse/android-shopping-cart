package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.HistoryRepository
import kotlin.concurrent.thread

class GetRecentSearchHistoryUseCase(
    private val repository: HistoryRepository,
) {
    operator fun invoke(callback: (HistoryProduct?) -> Unit) {
        thread {
            callback(repository.fetchRecentHistory())
        }
    }
}
