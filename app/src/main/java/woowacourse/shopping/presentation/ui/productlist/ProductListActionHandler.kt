package woowacourse.shopping.presentation.ui.productlist

interface ProductListActionHandler {
    fun navigateToProductDetail(productId: Long)

    fun loadMoreProducts()
}
