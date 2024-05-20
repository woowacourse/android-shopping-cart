package woowacourse.shopping.presentation.ui.productlist

interface ProductListActionHandler {
    fun navigateToProductDetail(productId: Int)

    fun loadMoreProducts()
}
