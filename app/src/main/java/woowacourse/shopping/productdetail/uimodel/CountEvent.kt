package woowacourse.shopping.productdetail.uimodel

sealed interface CountEvent {
    data object MinusFail : CountEvent

    data object PlusFail : CountEvent
}
