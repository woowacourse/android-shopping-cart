package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun loadProductDetail(productId: Int)
        fun putProductInCart()
    }

    interface View {
        fun setProductDetail(productModel: ProductModel)
        fun showCompleteMessage(productName: String)
    }
}
