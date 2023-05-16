package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.model.ProductState

interface ProductDetailContract {

    interface View {
        fun setViewContent(product: ProductState)
        fun showCart()
        fun showAccessError()
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?

        fun loadProduct()
        fun addCartProduct()
    }
}
