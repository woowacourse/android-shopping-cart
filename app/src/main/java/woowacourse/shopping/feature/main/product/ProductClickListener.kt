package woowacourse.shopping.feature.main.product

interface ProductClickListener {
    fun onClick(productId: Long)
    fun onChangeCartCount(productId: Long, count: Int)
}