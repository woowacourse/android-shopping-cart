package woowacourse.shopping.view.cart.state

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.view.main.state.IncreaseState
import woowacourse.shopping.view.main.state.ProductState

data class CartUiState(
    val items: List<ProductState> = emptyList(),
    val pageState: PageState = PageState(),
) {
    fun modifyUiState(newState: ProductState): CartUiState {
        val targetIndex = targetIndex(newState.item.id)
        val mutableItems = items.toMutableList()
        mutableItems[targetIndex] = newState

        return copy(items = mutableItems)
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

        if (!result.cartQuantity.hasQuantity()) {
            return result.copy(cartQuantity = Quantity(1))
        }

        return result
    }

    private fun targetIndex(productId: Long) = items.indexOfFirst { it.item.id == productId }
}
