package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.Product

interface CartHandler {
    fun onDeleteClick(product: Product)
}
