package woowacourse.shopping.presentation.ui.productDetail

interface ProductDetailContract {
    interface View {
        val presenter: Presenter
        fun handleNoSuchProductError()
    }

    interface Presenter {
        fun addProductInCart()
    }
}
