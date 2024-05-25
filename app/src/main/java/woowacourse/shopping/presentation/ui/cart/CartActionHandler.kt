package woowacourse.shopping.presentation.ui.cart

interface CartActionHandler {
    fun onDelete(productId: Long)

    fun onNext()

    fun onPrevious()
}
