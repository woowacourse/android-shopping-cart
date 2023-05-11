package woowacourse.shopping.view.cart

import woowacourse.shopping.model.ProductModel

interface CartContract {
    interface View {
        fun showProducts(cartProducts: List<ProductModel>)
        fun notifyRemoveItem(id: Int)
        fun notifyAddProducts(position: Int, size: Int)
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun showMoreProducts()
    }
}
