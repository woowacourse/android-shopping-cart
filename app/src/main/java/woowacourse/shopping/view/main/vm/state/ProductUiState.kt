package woowacourse.shopping.view.main.vm.state

data class ProductUiState(
    val items: List<ProductState> = emptyList(),
    val load: LoadState = LoadState.CannotLoad,
) {
    val sumOfCartQuantity
        get() = items.sumOf { it.cartQuantity.value }

    fun modifyUiState(newState: ProductState): ProductUiState {
        val targetIndex = targetIndex(newState.item.id)
        val mutableItems = items.toMutableList()
        mutableItems[targetIndex] = newState
        return copy(items = mutableItems)
    }

    fun isAddedProduct(productId: Long): CartSavingState {
        val targetIndex = targetIndex(productId)
        val target = items[targetIndex]

        return target.isSaveInCart()
    }

    fun canIncreaseCartQuantity(productId: Long): IncreaseState {
        val targetIndex = targetIndex(productId)
        val target = items[targetIndex]
        val result = target.increaseCartQuantity()

        return result
    }

    fun decreaseCartQuantity(productId: Long): ProductState {
        val targetIndex = targetIndex(productId)
        val result = items[targetIndex].decreaseCartQuantity()

        return result
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

    private fun targetIndex(productId: Long) = items.indexOfFirst { it.item.id == productId }
}
