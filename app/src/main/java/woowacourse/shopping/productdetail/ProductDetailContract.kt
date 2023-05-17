package woowacourse.shopping.productdetail

import woowacourse.shopping.uimodel.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun showCartPage()
    }

    interface Presenter {
        val product: ProductUIModel
        fun insertRecentRepository(currentTime: Long)
        fun addToCart()
    }
}
