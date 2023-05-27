package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.presentation.listener.CartCounterListener
import woowacourse.shopping.presentation.model.ProductModel

interface ProductClickListener : CartCounterListener {

    fun onItemClick(productModel: ProductModel)
}
