package woowacourse.shopping.ui.cart.cartAdapter

import woowacourse.shopping.model.ProductUIModel

interface CartListener {
    fun onItemClick(product: ProductUIModel)
    fun onItemRemove(productId: Int)
    fun onPageUp()
    fun onPageDown()
}
