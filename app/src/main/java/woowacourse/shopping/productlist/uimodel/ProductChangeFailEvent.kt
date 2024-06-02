package woowacourse.shopping.productlist.uimodel

sealed interface ProductChangeFailEvent {
    data object PlusFail : ProductChangeFailEvent
}
