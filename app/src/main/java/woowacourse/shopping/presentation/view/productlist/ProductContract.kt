package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductContract {
    interface View {
        fun setProductItemsView(products: List<ProductModel>)
    }
    interface Presenter {
        fun loadProductItems()
    }
}
