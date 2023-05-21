package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.util.SafeLiveData

interface ProductListContract {
    interface Presenter {
        val cartProductInfoList: SafeLiveData<CartProductInfoList>
        fun updateProductItems()
        fun updateRecentProductItems()
        fun updateCartProductCount(productModel: ProductModel, count: Int)
        fun putProductInCart(productModel: ProductModel)
        fun updateCartProductInfoList()
    }

    interface View {
        fun loadProductModels(productModels: List<ProductModel>)
        fun loadRecentProductModels(productModels: List<ProductModel>)
    }
}
