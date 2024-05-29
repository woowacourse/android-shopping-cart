package woowacourse.shopping.productlist

interface ProductListClickAction {
    fun onProductClicked(id: Long)

    fun onAddButtonClicked(id: Long)

    fun onPlusButtonClicked(
        id: Long,
        currentQuantity: Int,
    )

    fun onMinusButtonClicked(
        id: Long,
        currentQuantity: Int,
    )
}
