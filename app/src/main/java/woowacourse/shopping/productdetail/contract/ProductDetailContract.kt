package woowacourse.shopping.productdetail.contract

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setProductDetail(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpProductDetail()
        fun addProductToCart()
        fun addProductToRecent()
    }
}
