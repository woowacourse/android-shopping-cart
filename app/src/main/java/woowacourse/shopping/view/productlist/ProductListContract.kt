package woowacourse.shopping.view.productlist

import woowacourse.shopping.model.ProductModel

interface ProductListContract {
    interface View {
        fun showProducts(recentViewedProducts: List<ProductModel>, products: List<ProductModel>)
        fun addProducts(position: Int, size: Int)
    }

    interface Presenter {
        fun fetchProducts()
        fun showMoreProducts()
    }
}
