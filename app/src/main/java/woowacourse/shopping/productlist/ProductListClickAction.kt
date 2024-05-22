package woowacourse.shopping.productlist

interface ProductListClickAction {
    fun onProductClicked(id: Long)

    fun onIntoCartClicked(id: Long)

    fun onPlusCountClicked(id: Long)

    fun onMinusCountClicked(id: Long)
}
