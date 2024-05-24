package woowacourse.shopping.domain.model

sealed interface ProductCountEvent {
    data class ProductCountCountChanged(val productId: Int, val count: Int) : ProductCountEvent

    data class ProductCountCleared(val productId: Int) : ProductCountEvent

    data object ProductCountAllCleared : ProductCountEvent
}
