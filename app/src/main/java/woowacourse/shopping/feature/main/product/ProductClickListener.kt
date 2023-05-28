package woowacourse.shopping.feature.main.product

interface ProductClickListener {
    fun onClick(productId: Long)
    fun onCartCountChanged(productId: Long, count: Int)
}
