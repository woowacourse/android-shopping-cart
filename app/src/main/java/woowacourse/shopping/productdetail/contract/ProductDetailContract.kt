package woowacourse.shopping.productdetail.contract

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setProductDetail(product: ProductUIModel)
        fun navigateToCart()
    }

    interface Presenter {
        fun setUpProduct()
        fun addCart()
    }
}
