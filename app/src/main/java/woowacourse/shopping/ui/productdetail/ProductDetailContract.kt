package woowacourse.shopping.ui.productdetail

interface ProductDetailContract {
    interface View {
        val presenter: Presenter

        fun showProductImage(imageUrl: String)
        fun navigateToBasketScreen()
        fun showProductName(name: String)
        fun showProductPrice(amount: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun addBasketProduct()
    }
}
