package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState

interface ProductDetailContract {
    interface Presenter {
        fun loadProduct(productId: Long)
        fun addProductToCart(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
        fun showErrorMessage()
    }
}
