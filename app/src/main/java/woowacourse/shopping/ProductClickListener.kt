package woowacourse.shopping

import woowacourse.shopping.uimodel.ProductUIModel

interface ProductClickListener {
    fun onProductClick(productUIModel: ProductUIModel)
}
