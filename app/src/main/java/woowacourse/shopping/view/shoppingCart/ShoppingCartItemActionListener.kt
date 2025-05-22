package woowacourse.shopping.view.shoppingCart

interface ShoppingCartItemActionListener {
    fun onRemoveProduct(item: ShoppingCartItem.ProductItem)

    fun onPlusProductQuantity(item: ShoppingCartItem.ProductItem)

    fun onMinusProductQuantity(item: ShoppingCartItem.ProductItem)
}
