package woowacourse.shopping.ui.productList.event

sealed class ProductListError {
    data object LoadProducts : ProductListError()

    data object FinalPage : ProductListError()

    data object CartProductQuantity : ProductListError()

    data object LoadProductHistory : ProductListError()

    data object UpdateProductQuantity : ProductListError()

    data object AddProductInCart : ProductListError()
}
