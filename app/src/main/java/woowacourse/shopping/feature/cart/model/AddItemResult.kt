package woowacourse.shopping.feature.cart.model

sealed class AddItemResult {
    data class NewAdded(
        val cart: Cart,
    ) : AddItemResult()

    data object DuplicateItem : AddItemResult()
}
