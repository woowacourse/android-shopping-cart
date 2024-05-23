package woowacourse.shopping.view

interface CountActionHandler {
    fun onIncreaseQuantityButtonClicked(id: Long)

    fun onDecreaseQuantityButtonClicked(id: Long)
}
