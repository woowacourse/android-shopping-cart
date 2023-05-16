package woowacourse.shopping.feature.main.product

interface ProductClickListener {
    fun onClick(productId: Long)
    fun plusCart(productId: Long, count: Int)
    fun minusCart(productId: Long, count: Int)
}