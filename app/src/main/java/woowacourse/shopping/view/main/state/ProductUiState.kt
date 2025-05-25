package woowacourse.shopping.view.main.state

data class ProductUiState(
    val productItems: List<ProductState> = emptyList(),
    val historyItems: List<HistoryState> = emptyList(),
    val load: LoadState = LoadState.CannotLoad,
) {
    val lastSeenProductId
        get() = historyItems.firstOrNull()?.productId

    val productIds
        get() = productItems.map { it.item.id }

    val sumOfCartQuantity
        get() = productItems.sumOf { it.cartQuantity.value }

    fun modifyUiState(newState: ProductState): ProductUiState {
        val targetIndex = targetIndex(newState.item.id)
        val mutableItems = productItems.toMutableList()
        mutableItems[targetIndex] = newState
        return copy(productItems = mutableItems)
    }

    fun canIncreaseCartQuantity(productId: Long): IncreaseState {
        val targetIndex = targetIndex(productId)
        val target = productItems[targetIndex]
        val result = target.increaseCartQuantity()

        return result
    }

    fun decreaseCartQuantity(productId: Long): ProductState {
        val targetIndex = targetIndex(productId)
        val result = productItems[targetIndex].decreaseCartQuantity()

        return result
    }

    fun productItemCount() = productItems.size

    fun addItems(
        newItems: List<ProductState>,
        hasNextPage: Boolean,
    ): ProductUiState {
        return copy(
            productItems = productItems + newItems,
            load = LoadState.of(hasNextPage),
        )
    }

    private fun targetIndex(productId: Long) = productItems.indexOfFirst { it.item.id == productId }
}
