package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun addToCart()
    }

    interface View {
        fun initRecentProduct(recentProduct: ProductModel?)
        fun updateProductDetail(product: ProductModel)
        fun showCart()
    }
}
