package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun updateProducts()
        fun updateRecentProducts()
        fun saveRecentProductId(id: Int)
    }

    interface View {
        fun loadProductModels(productModels: List<ProductModel>)
        fun loadRecentProductModels(productModels: List<ProductModel>)
    }
}
