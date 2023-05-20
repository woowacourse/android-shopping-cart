package woowacourse.shopping.presentation.view.productlist

interface ProductListener {
    fun onCountClick(productId: Long, count: Int)
    fun onItemClick(productId: Long)
}
