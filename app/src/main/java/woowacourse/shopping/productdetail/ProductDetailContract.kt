package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun showCartCounter()
        fun addToCart(cartProduct: CartProductModel)
        fun showRecentProductDetail()
    }

    interface View {
        fun initRecentProduct(recentProduct: ProductModel?)
        fun updateProductDetail(product: ProductModel)
        fun openCartCounter(cartProduct: CartProductModel)
        fun close()
        fun showProductDetail(product: ProductModel)
    }
}
