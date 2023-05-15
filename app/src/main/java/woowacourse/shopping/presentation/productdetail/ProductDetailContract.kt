package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        var productModel: ProductModel
        fun putProductInCart()
    }

    interface View {
        fun showCompleteMessage(productName: String)
    }
}
