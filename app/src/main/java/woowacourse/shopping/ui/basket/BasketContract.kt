package woowacourse.shopping.ui.basket

import woowacourse.shopping.domain.Basket
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.model.UiBasketProduct

interface BasketContract {
    interface View {

        fun updateBasketProducts(basket: Basket)

        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)

        fun updateCurrentPage(currentPage: Int)

        fun updateTotalPrice(totalPrice: Int)

        fun updateCheckedProductsCount(checkedProductsCount: Int)

        fun updateTotalCheckBox(isChecked: Boolean)
    }

    interface Presenter {
        val view: View

        fun updateBasketProductCheckState(basketProduct: BasketProduct)

        fun addBasketProduct(product: Product)

        fun removeBasketProduct(product: Product)

        fun initBasketProducts()

        fun updatePreviousPage()

        fun updateNextPage()

        fun deleteBasketProduct(product: UiBasketProduct, currentProducts: List<UiBasketProduct>)
    }
}
