package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface View {
        val presenter: Presenter

        fun showProductImage(imageUrl: String)
        fun navigateToBasketScreen()
        fun showProductName(name: String)
        fun showProductPrice(amount: Int)
    }

    interface Presenter {
        val view: View

        fun addBasketProduct()
    }
}
