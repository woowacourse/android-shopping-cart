package woowacourse.shopping.view.productdetail

import woowacourse.shopping.model.uimodel.ProductUIModel

interface ProductDetailContract {
    interface View {
        var presenter: Presenter
        fun setProductDetailView()
        fun showCartPage()
    }

    interface Presenter {
        val product: ProductUIModel
        fun saveRecentProduct()
        fun saveCartProduct()
    }
}
