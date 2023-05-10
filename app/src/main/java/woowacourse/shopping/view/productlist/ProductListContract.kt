package woowacourse.shopping.view.productlist

import woowacourse.shopping.model.ProductModel

interface ProductListContract {
    interface View {
        fun showProducts(recentViewedProducts: List<ProductModel>, products: List<ProductModel>)
    }

    interface Presenter {
        fun fetchProducts()
    }
}
