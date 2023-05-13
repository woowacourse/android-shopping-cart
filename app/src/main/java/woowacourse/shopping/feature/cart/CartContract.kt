package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductUiModel>)
        fun setPreviousButtonState(enabled: Boolean)
        fun setNextButtonState(enabled: Boolean)
        fun setCount(count: Int)
    }

    interface Presenter {
        val page: PageUiModel
        fun loadInitCartProduct()
        fun deleteCartProduct(cartProduct: CartProductUiModel)
        fun loadPreviousPage()
        fun loadNextPage()
        fun setPage(page: PageUiModel)
    }
}
