package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun putProductInCart(productModel: ProductModel)
    }

    interface View {
        fun showCompleteMessage(productName: String)
    }
}
