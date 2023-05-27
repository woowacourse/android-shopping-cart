package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun loadProducts()
        fun loadRecentProducts()
        fun saveRecentProductId(productId: Long)
        fun addCartProductCount(cartProductModel: CartProductModel)
        fun subCartProductCount(cartProductModel: CartProductModel)
    }

    interface View {
        fun setCartCount(count: Int)
        fun setProductModels(cartProductModels: List<CartProductModel>)
        fun replaceProductModel(cartProductModel: CartProductModel)
        fun setRecentProductModels(productModels: List<ProductModel>)
    }
}
