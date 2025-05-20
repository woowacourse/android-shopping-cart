package woowacourse.shopping.view.main.vm.state

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Product

data class ProductState(
    val item: Product,
    val cartQuantity: Quantity,
) {
    fun isSaveInCart(): CartSavingState {
        return if (cartQuantity.hasQuantity()) {
            CartSavingState.SAVED
        } else {
            CartSavingState.NOT_SAVED
        }
    }

    fun increase(): IncreaseState {
        return if (item.canIncrease(cartQuantity.value + 1)) {
            IncreaseState.CanIncrease(copy(cartQuantity = cartQuantity + 1))
        } else {
            IncreaseState.CannotIncrease
        }
    }

    val quantityVisible: Boolean
        get() = cartQuantity.hasQuantity()
}
