package woowacourse.shopping.view.main.vm.state

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
            val stock = item.quantity - increasedQuantity.value

            IncreaseState.CanIncrease(
                copy(cartQuantity = increasedQuantity),
                stock,
            )
        } else {
            IncreaseState.CannotIncrease(item.quantityValue)
        }
    }

    fun decreaseCartQuantity(): Pair<ProductState, Quantity> {
        val decreasedCartQuantity = cartQuantity - 1
        val productStock = item.quantity - decreasedCartQuantity.value
        val newState = copy(cartQuantity = decreasedCartQuantity)

        return newState to productStock
    }
}
