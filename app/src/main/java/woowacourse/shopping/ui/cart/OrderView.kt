package woowacourse.shopping.ui.cart

interface OrderView {
    fun setOrderPrice(price: Int)

    fun setOrderCount(count: Int)
}