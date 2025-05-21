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
        return if (item.canIncrease(cartQuantity.value + 1)) {
            IncreaseState.CanIncrease(copy(cartQuantity = cartQuantity + 1))
        } else {
            IncreaseState.CannotIncrease(item.quantityValue)
        }
    }

    fun decreaseCartQuantity(): ProductState {
        return copy(cartQuantity = cartQuantity - 1)
    }
}
