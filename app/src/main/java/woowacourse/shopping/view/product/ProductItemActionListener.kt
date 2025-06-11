package woowacourse.shopping.view.product

interface ProductItemActionListener {
    fun onSelectProduct(item: ProductsItem.ProductItem)

    fun onPlusProductQuantity(item: ProductsItem.ProductItem)

    fun onMinusProductQuantity(item: ProductsItem.ProductItem)
}
