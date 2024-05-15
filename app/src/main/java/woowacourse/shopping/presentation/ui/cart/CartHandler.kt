package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.presentation.ui.Product

interface CartHandler {
    fun onDeleteClick(product: Product)
}
