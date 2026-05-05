package woowacourse.shopping.domain.model

sealed class RemoveItemResult {
    data class Success(
        val cart: Cart,
    ) : RemoveItemResult()

    data object NotFoundItem : RemoveItemResult()
}
