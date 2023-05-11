package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductItemModel>)
        fun deleteCartProductFromScreen(position: Int)
        fun setPreviousButtonState(enabled: Boolean)
        fun setNextButtonState(enabled: Boolean)
        fun setCount(count: Int)
    }

    interface Presenter {
        val page: PageUiModel
        fun loadInitCartProduct()
        fun deleteCartProduct(cartProduct: CartProductUiModel, topId: Long)
        fun loadPreviousPage(topId: Long)
        fun loadNextPage(bottomId: Long)
        fun setPage(page: PageUiModel)
    }
}
