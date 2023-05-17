package woowacourse.shopping.presentation.ui.productDetail.presenter

interface ProductDetailContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        fun getProduct(id: Long)
        fun addRecentlyViewedProduct(id: Long, unit: Int)
        fun addProductInCart()
    }
}
