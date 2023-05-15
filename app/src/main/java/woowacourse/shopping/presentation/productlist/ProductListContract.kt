package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun loadProducts()
        fun loadRecentProducts()
        fun saveRecentProductId(productId: Int)
    }

    interface View {
        fun setProductModels(productModels: List<ProductModel>)

        fun setRecentProductModels(productModels: List<ProductModel>)
    }
}
