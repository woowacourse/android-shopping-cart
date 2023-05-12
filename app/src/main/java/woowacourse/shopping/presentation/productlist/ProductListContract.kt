package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.presentation.model.ProductModel

interface ProductListContract {
    interface Presenter {
        fun initProducts()
        fun updateProducts()
        fun updateRecentProducts()
        fun saveRecentProductId(id: Int)
    }

    interface View {
        fun initProductModels(
            recentProductModels: List<ProductModel>,
            productModels: List<ProductModel>,
        )

        fun setProductModels(productModels: List<ProductModel>)

        fun setRecentProductModels(productModels: List<ProductModel>)
    }
}
