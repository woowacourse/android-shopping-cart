package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun saveProductInRepository(count: Int)
        fun saveRecentProduct()
        fun checkCurrentProductIsMostRecent()
        fun showMostRecentProductDetail()
        fun showProductCart()
        fun updateTotalPrice(count: Int)
    }

    interface View {
        fun showCompleteMessage(productName: String)
        fun setMostRecentProductVisible(visible: Boolean, mostRecentProductModel: ProductModel)
        fun navigateToMostRecent(mostRecentProductModel: ProductModel)
        fun showProductCart(cartProductModel: CartProductInfoModel)
        fun setTotalPrice(price: Int)
    }
}
