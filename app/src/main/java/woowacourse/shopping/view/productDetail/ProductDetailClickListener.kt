package woowacourse.shopping.view.productDetail

interface ProductDetailClickListener {
    fun onCloseButton()

    fun onAddingToShoppingCart()

    fun onPlusQuantity()

    fun onMinusQuantity()
}
