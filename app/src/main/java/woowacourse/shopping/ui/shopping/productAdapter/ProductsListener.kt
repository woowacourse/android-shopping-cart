package woowacourse.shopping.ui.shopping.productAdapter

import woowacourse.shopping.model.ProductUIModel

interface ProductsListener {
    fun onClickItem(data: ProductUIModel)
    fun onReadMoreClick()
}
