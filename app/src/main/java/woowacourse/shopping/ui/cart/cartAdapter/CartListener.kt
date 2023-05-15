package woowacourse.shopping.ui.cart.cartAdapter

import woowacourse.shopping.model.CartProductUIModel

interface CartListener {
    fun onItemClick(product: CartProductUIModel)
    fun onItemRemove(productId: Int)
    fun onPageNext()
    fun onPagePrev()
    fun onItemUpdate(productId: Int, count: Int): Int
    fun onItemSelectChanged(productId: Int, selected: Boolean)
}
