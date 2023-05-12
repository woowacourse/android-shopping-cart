package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.ProductUIModel

interface ProductsListener {
    val onClickItem: (data: ProductUIModel) -> Unit
    val onReadMoreClick: () -> Unit
}
