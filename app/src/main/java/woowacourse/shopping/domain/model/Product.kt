package woowacourse.shopping.domain.model

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val cartItemCounter: CartItemCounter = CartItemCounter(),
    val itemSelector: ItemSelector = ItemSelector(),
) {
    fun updateCartItemCount(newCount: Int) {
        cartItemCounter.updateCount(newCount)
    }

    fun updateItemSelector(isSelected: Boolean) {
        when (isSelected) {
            true -> itemSelector.selectItem()
            false -> itemSelector.unSelectItem()
        }
    }

    companion object {
        const val DEFAULT_PRODUCT_ID = -1L
        val defaultProduct =
            Product(
                DEFAULT_PRODUCT_ID,
                "",
                0,
                "",
            )
    }
}
