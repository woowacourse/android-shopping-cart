package woowacourse.shopping.view.cart.event

fun interface CartAdapterEventHandler {
    fun onClickDeleteItem(id: Long)
}
