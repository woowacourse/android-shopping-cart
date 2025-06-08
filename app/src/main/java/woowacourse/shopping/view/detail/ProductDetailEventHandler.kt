package woowacourse.shopping.view.detail

interface ProductDetailEventHandler {
    fun onSelectRecentProduct()

    fun onAddToCart()

    fun onIncreaseQuantity()

    fun onDecreaseQuantity()
}
