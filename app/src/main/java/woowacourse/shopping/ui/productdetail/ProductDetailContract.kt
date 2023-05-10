package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface Presenter {
        fun loadProduct(productId: Long)
        fun addProductToCart(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
        fun showErrorMessage()
    }
}
