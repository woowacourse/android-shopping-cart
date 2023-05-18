package woowacourse.shopping.view.productdetail

import woowacourse.shopping.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun finishActivity(isAdd: Boolean)
        fun updateCount(count: Int)
    }

    interface Presenter {
        fun putInCart(product: ProductModel)
        fun updateRecentViewedProducts(id: Int)
        fun plusCount()
        fun minusCount()
    }
}
