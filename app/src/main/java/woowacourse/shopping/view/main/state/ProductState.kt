package woowacourse.shopping.view.main.state

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Product

data class ProductState(
    val item: Product,
    val cartQuantity: Quantity,
) {
    val cartQuantityValue: Int
        get() = cartQuantity.value

    fun isSaveInCart(): CartSavingState {
        return if (cartQuantity.hasQuantity()) {
            CartSavingState.SAVED
        } else {
            CartSavingState.NOT_SAVED
        }
    }

    fun increaseCartQuantity(): IncreaseState {
        val increasedQuantity = cartQuantity + 1
        val canIncrease = item.canIncrease(increasedQuantity)

        return if (canIncrease) {
            IncreaseState.CanIncrease(copy(cartQuantity = increasedQuantity))
        } else {
            IncreaseState.CannotIncrease(item.quantityValue)
        }
    }

    fun decreaseCartQuantity(): ProductState {
        val decreasedCartQuantity = cartQuantity - 1
        val newState = copy(cartQuantity = decreasedCartQuantity)

        return newState
    }
}
