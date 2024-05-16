package woowacourse.shopping.presentation.ui.productlist

interface ProductListActionHandler {
    fun onClickProduct(productId: Int)

    fun onClickLoadMoreButton()
}
