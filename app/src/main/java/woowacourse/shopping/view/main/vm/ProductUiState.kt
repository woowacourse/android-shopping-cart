package woowacourse.shopping.view.main.vm

data class ProductUiState(
    val items: List<ProductState> = emptyList(),
    val load: LoadState = LoadState.CannotLoad,
) {
    fun increaseQuantity(productId: Long): ProductUiState {
        return copy(
            items.map {
                if (it.item.id == productId) {
                    it.increase()
                } else {
                    it
                }
            },
        )
    }

    fun decreaseQuantity(productId: Long): ProductUiState {
        return copy(
            items.map {
                if (it.item.id == productId) {
                    it.decrease()
                } else {
                    it
                }
            },
        )
    }

    fun itemCount() = items.size

    fun addItems(
        newItems: List<ProductState>,
        hasNextPage: Boolean,
    ): ProductUiState {
        return copy(
            items = items + newItems,
            load = LoadState.of(hasNextPage),
        )
    }
}
