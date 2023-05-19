package woowacourse.shopping.productdetail.contract

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setupView()
        fun setProductDetail(product: ProductUIModel)
        fun navigateToCart()
        fun showCartDialog(product: ProductUIModel)
    }

    interface Presenter {
        val count: Int
        fun setUp()
        fun addCart()
        fun onClickCart()

        fun increaseCount()
        fun decreaseCount()
    }
}
