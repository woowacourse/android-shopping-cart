package woowacourse.shopping.domain.model

sealed interface UpdateCartItemType {
    data object INCREASE : UpdateCartItemType

    data object DECREASE : UpdateCartItemType

    data class UPDATE(val count: Int) : UpdateCartItemType
}
