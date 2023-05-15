package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun addToCart()
    }

    interface View {
        fun updateProductDetail(productModel: ProductModel)

        fun showCart()
    }
}
