package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.listener.CartCounterListener
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

interface CartListener : CartCounterListener {
    fun onCloseClick(cartProductModel: CartProductModel)
    fun changeSelectionProduct(productModel: ProductModel)
}
