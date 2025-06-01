package woowacourse.shopping.view.loader

import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.main.state.HistoryState

class HistoryLoader(
    val productRepository: ProductRepository,
    val historyRepository: HistoryRepository,
) {
    operator fun invoke(onResult: (List<HistoryState>) -> Unit) {
        historyRepository.getHistory { historyIds ->
            if (historyIds.isEmpty()) {
                onResult(emptyList())
                return@getHistory
            }

            productRepository.getProducts(historyIds) { products ->
                val historyStates =
                    products.map {
                        HistoryState(it.id, it.name, it.imgUrl)
                    }
                onResult(historyStates)
            }
        }
    }
}
