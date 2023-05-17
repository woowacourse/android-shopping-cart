package woowacourse.shopping.productdetail

interface ProductDetailContract {
    interface View {
        fun showCartPage()
    }

    interface Presenter {
        fun initPage()
        fun insertRecentRepository(currentTime: Long)
        fun onClickAddToCart()
    }
}
