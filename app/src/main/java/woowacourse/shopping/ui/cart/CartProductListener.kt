package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.OnItemQuantityChangeListener

interface CartProductListener: OnItemQuantityChangeListener {
    fun deleteProduct(productId: Long)
}