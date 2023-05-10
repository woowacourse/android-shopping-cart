package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface Presenter {
        fun loadProduct(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
    }
}
