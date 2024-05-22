package woowacourse.shopping.presentation.ui.productlist

interface ProductListActionHandler {
    fun onClickProduct(productId: Int)

    fun onClickLoadMoreButton()

    fun onClickPlusOrderButton(productId: Int)

    fun onClickMinusOrderButton(productId: Int)
}
