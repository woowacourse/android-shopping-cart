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
        fun initProducts(products: List<ProductModel>)
        fun initRecentProducts(products: List<ProductModel>)
        fun updateRecentProducts(products: List<ProductModel>)
    }
}
