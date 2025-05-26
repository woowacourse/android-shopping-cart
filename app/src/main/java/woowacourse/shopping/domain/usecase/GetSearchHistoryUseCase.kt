package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.HistoryProduct
import woowacourse.shopping.domain.repository.HistoryRepository
import kotlin.concurrent.thread

class GetSearchHistoryUseCase(
    private val repository: HistoryRepository,
) {
    operator fun invoke(callback: (List<HistoryProduct>) -> Unit) {
        thread {
            callback(repository.fetchAllHistory())
        }
    }
}
