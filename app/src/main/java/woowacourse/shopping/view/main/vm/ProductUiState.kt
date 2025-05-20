package woowacourse.shopping.view.main.vm

import woowacourse.shopping.domain.product.Product

data class ProductUiState(
    val items: List<Product> = emptyList(),
    val load: LoadState = LoadState.CannotLoad,
) {
    fun itemCount() = items.size

    fun addItems(
        newItems: List<Product>,
        hasNextPage: Boolean,
    ): ProductUiState {
        return copy(
            items = items + newItems,
            load = LoadState.of(hasNextPage),
        )
    }
}
