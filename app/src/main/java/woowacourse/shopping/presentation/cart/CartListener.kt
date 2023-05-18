package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.listener.CartCounterListener
import woowacourse.shopping.presentation.model.ProductModel

interface CartListener : CartCounterListener {
    fun onCloseClick(productModel: ProductModel)
    fun changeSelectionProduct(productModel: ProductModel)
}
