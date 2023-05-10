package woowacourse.shopping.view.cart

import woowacourse.shopping.model.ProductModel

interface CartContract {
    interface View {
        fun showProducts(cartProducts: List<ProductModel>)
    }

    interface Presenter {
        fun fetchProducts()
    }
}
