package woowacourse.shopping.productdetail.contract

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setupView()
        fun setProductDetail(product: ProductUIModel)
        fun navigateToCart()
        fun showCartDialog(product: ProductUIModel)
        fun setUpCountView()
        fun setCount(count: Int)
    }

    interface Presenter {
        val count: Int
        fun setUp()
        fun setUpCountView()
        fun addCart()
        fun onClickCart()

        fun increaseCount()
        fun decreaseCount()
    }
}
