package woowacourse.shopping.productdetail

import woowacourse.shopping.uimodel.ProductUIModel

interface ProductDetailContract {
    interface View {
        var presenter: Presenter

        fun setViews(productData: ProductUIModel)
        fun showCartPage()
    }

    interface Presenter {
        fun initPage()
        fun insertRecentRepository(currentTime: Long)
        fun onClickAddToCart()
    }
}
