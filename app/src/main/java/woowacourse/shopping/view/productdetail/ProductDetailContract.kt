package woowacourse.shopping.view.productdetail

import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun startCartActivity()
    }

    interface Presenter {
        fun putInCart(product: ProductModel)
        fun updateRecentViewedProducts(id: Int)
    }
}
