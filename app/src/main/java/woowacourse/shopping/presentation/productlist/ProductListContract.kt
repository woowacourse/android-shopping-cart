package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun initProducts()
        fun initRecentProducts()
        fun updateRecentProducts()
        fun saveRecentProductId(id: Int)
    }

    interface View {
        fun initProductModels(productModels: List<ProductModel>)
        fun initRecentProductModels(productModels: List<ProductModel>)
        fun setRecentProductModels(productModels: List<ProductModel>)
    }
}
