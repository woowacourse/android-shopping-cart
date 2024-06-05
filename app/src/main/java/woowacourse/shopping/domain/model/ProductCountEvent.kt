package woowacourse.shopping.domain.model

sealed interface ProductCountEvent {
    data class ProductCountCountChanged(val productId: Long, val count: Int) : ProductCountEvent

    data class ProductCountCleared(val productId: Long) : ProductCountEvent

    data object ProductCountAllCleared : ProductCountEvent
}
