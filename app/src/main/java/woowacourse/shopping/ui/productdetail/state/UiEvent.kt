package woowacourse.shopping.ui.productdetail.state

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    object CartAddSuccess : UiEvent
}
