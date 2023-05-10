package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductContract {
    interface View {
        fun setProductItems(products: List<ProductModel>)
    }
    interface Presenter {
        fun loadProductItems()
    }
}
