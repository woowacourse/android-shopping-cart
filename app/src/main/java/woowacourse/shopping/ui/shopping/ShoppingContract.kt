package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct

interface ShoppingContract {
    interface View {

        fun updateProducts(products: List<UiProduct>)

        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)

        fun showProductDetail(product: UiProduct)

        fun updateMoreButtonState(isVisible: Boolean)
    }

    interface Presenter {
        val view: View

        fun addBasketProduct(product: Product)

        fun removeBasketProduct(product: Product)

        fun fetchProducts()

        fun fetchRecentProducts()

        fun inquiryProductDetail(product: UiProduct)

        fun inquiryRecentProductDetail(recentProduct: UiProduct)

        fun fetchHasNext()
    }
}
