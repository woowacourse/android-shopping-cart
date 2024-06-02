package woowacourse.shopping.shoppingcart.uimodel

sealed interface CountChangeFailEvent {
    data object MinusChangeFail : CountChangeFailEvent

    data object PlusChangeFail : CountChangeFailEvent
}
