package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

interface ProductDetailContract {
    interface View {
        val presenter: ProductDetailPresenter

        fun hideRecentProductInfoView()
        fun setRecentProductInfo(name: String, price: Int)
        fun showRecentProductDetail(product: CartProductItem)
    }

    interface Presenter {
        val sameProduct: Boolean

        fun initScreen()
        fun addProduct(product: CartProductItem)
        fun navigateRecentProductDetail()
    }
}
