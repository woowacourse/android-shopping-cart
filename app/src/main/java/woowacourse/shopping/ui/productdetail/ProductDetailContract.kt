package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState

interface ProductDetailContract {
    interface Presenter {
        fun onLoadProduct(productId: Long)
        fun onAddProductToCart(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
    }
}
