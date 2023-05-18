package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun updateProductItems()
        fun updateRecentProductItems()
        fun saveRecentProduct(id: Int)
        fun updateCartCount()
    }

    interface View {
        fun loadProductModels(productModels: List<ProductModel>)
        fun loadRecentProductModels(productModels: List<ProductModel>)
        fun showCartCount(count: Int)
    }
}
