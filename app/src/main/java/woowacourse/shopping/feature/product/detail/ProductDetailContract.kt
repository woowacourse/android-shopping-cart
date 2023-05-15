package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.feature.model.ProductState

interface ProductDetailContract {

    interface View {
        fun showCart()
        fun setProductImage(imageUrl: String)
        fun setProductName(name: String)
        fun setProductPrice(price: Int)
        fun showAccessError()
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?

        fun loadProduct()
        fun addCartProduct()
    }
}
