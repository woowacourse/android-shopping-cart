package woowacourse.shopping.feature.main

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreen(position: Int)
        fun addProducts(products: List<MainProductItemModel>)
    }

    interface Presenter {
        fun loadProducts()
        fun moveToCart()
        fun loadMore(lastProductId: Long)
    }
}
