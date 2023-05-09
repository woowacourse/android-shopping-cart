package woowacourse.shopping.productdetail

interface ProductDetailContract {

    interface View {
        fun navigateToShoppingCartView()
    }

    interface Presenter {
        fun addToShoppingCart()
    }
}
