package woowacourse.shopping.view.core.handler

interface CartQuantityHandler {
    fun onClickIncrease(productId: Long)

    fun onClickDecrease(productId: Long)
}
