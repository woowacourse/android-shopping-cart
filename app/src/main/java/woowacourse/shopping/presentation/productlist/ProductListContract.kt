package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun refreshProductItems()
        fun loadRecentProductItems()
        fun putProductInCart(cartProductModel: CartProductInfoModel)
        fun updateCartCount()
        fun loadMoreProductItems()
        fun updateCartProductCount(cartProductModel: CartProductInfoModel, count: Int)
    }

    interface View {
        fun loadProductItems(cartProductModels: List<CartProductInfoModel>)
        fun loadRecentProductItems(productModels: List<ProductModel>)
        fun showCartCount(count: Int)
    }
}
