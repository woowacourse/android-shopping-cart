package woowacourse.shopping.presentation.ui.productDetail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentlyViewedProduct

interface ProductDetailContract {
    interface View {
        val presenter: Presenter
        fun handleNoSuchProductError()
        fun setBindingData(product: Product, lastViewedProduct: RecentlyViewedProduct)
    }

    interface Presenter {
        fun addProductInCart()
    }
}
