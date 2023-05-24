package woowacourse.shopping.ui.cart.cartAdapter

import woowacourse.shopping.model.CartProductUIModel

interface CartListener {
    fun onItemClick(product: CartProductUIModel)
    fun onItemUpdate(productId: Int, count: Int)
    fun onItemCheckChanged(productId: Int, checked: Boolean)
    fun onItemRemove(productId: Int)
    fun onPageNext()
    fun onPagePrev()
}
