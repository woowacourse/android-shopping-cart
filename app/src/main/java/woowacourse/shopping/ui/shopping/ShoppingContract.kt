package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.model.UiProduct
import woowacourse.shopping.ui.model.UiRecentProduct

interface ShoppingContract {
    interface View {

        fun updateProducts(products: List<UiProduct>)

        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)

        fun showProductDetail(currentProduct: UiProduct, previousProduct: UiProduct?)

        fun updateMoreButtonState(isVisible: Boolean)

        fun updateTotalBasketCount(totalBasketCount: Int)
    }

    interface Presenter {
        val view: View

        fun updateBasket()

        fun fetchTotalBasketCount()

        fun addBasketProduct(product: Product)

        fun removeBasketProduct(product: Product)

        fun fetchProducts()

        fun fetchRecentProducts()

        fun inquiryProductDetail(product: UiProduct)

        fun fetchHasNext()
    }
}
