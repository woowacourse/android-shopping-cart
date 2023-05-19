package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun loadProductDetail(productId: Int)
        fun loadRecentProduct(productId: Int)
        fun putProductInCart(count: Int)
    }

    interface View {
        fun showProductDetail(productModel: ProductModel)
        fun showRecentProduct(productModel: ProductModel)
    }
}
