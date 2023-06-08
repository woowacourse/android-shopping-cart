package woowacourse.shopping.listener

interface CountListener {
    fun onPlusCountButtonClick(productId: Long, oldCount: Int)
    fun onMinusCountButtonClick(productId: Long, oldCount: Int)
}
