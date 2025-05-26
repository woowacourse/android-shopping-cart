package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.repository.HistoryRepository
import kotlin.concurrent.thread

class AddSearchHistoryUseCase(
    private val repository: HistoryRepository,
) {
    operator fun invoke(productId: Int) {
        thread {
            repository.saveHistory(productId)
        }
    }
}
