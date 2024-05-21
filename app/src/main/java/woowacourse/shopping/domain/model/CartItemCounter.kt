package woowacourse.shopping.domain.model

import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState

class CartItemCounter(
    count: Int = DEFAULT_ITEM_COUNT,
    isSelected: Boolean =  DEFAULT_ITEM_SELECTED,
) {
    var itemCount: Int = count
        private set
    var isSelected: Boolean = isSelected
        private set

    fun increase(): ChangeCartItemResultState {
        itemCount++
        return ChangeCartItemResultState.Success
    }

    fun decrease(): ChangeCartItemResultState {
        return if (itemCount > DEFAULT_ITEM_COUNT) {
            itemCount--
            ChangeCartItemResultState.Success
        } else{
            ChangeCartItemResultState.Fail
        }
    }

    fun selectItem(){
        isSelected = true
    }

    fun unSelectItem(){
        isSelected = false
    }

    companion object {
        const val DEFAULT_ITEM_COUNT = 0
        const val DEFAULT_ITEM_SELECTED = false
    }
}
