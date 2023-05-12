package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.ProductUIModel

interface CartListener {
    val onItemClick: (ProductUIModel) -> Unit
    val onItemRemove: (Int) -> Unit
    val onPageUp: () -> Unit
    val onPageDown: () -> Unit
}
