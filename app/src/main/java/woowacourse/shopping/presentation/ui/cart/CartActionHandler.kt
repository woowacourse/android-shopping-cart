package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.Product

interface CartActionHandler {
    fun onDelete(product: Product)

    fun onNext()

    fun onPrevious()
}
