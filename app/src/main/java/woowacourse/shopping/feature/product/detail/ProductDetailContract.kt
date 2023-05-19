package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.model.ProductState

interface ProductDetailContract {

    interface View {
        fun setViewContent(product: ProductState)
        fun setCount(count: Int)
        fun showCart()
        fun showAccessError()
        fun showSelectCountDialog()
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?

        fun loadProduct()
        fun addCartProduct(count: Int)
        fun plusCount()
        fun minusCount()
    }
}
