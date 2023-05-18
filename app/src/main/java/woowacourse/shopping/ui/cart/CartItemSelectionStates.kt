package woowacourse.shopping.ui.cart

class CartItemSelectionStates {

    private val selectedStates: MutableMap<Long, Boolean> = mutableMapOf()

    operator fun get(productId: Long): Boolean {
        return selectedStates[productId] ?: false
    }

    operator fun set(productId: Long, isSelected: Boolean) {
        selectedStates[productId] = isSelected
    }
}