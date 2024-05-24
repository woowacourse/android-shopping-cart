package woowacourse.shopping.domain.model

class ItemSelector(isSelected: Boolean = DEFAULT_ITEM_SELECTED) {
    var isSelected: Boolean = isSelected
        private set

    fun selectItem() {
        isSelected = true
    }

    fun unSelectItem() {
        isSelected = false
    }

    companion object {
        const val DEFAULT_ITEM_SELECTED = false
    }
}
