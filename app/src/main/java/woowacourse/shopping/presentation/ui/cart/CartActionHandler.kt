package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.Product

interface CartActionHandler {
    fun onDelete(productId: Long)

    fun onNext()

    fun onPrevious()
}
