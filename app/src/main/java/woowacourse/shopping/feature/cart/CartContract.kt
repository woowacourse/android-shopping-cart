package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductUiModel>)
        fun setPreviousButtonState(enabled: Boolean)
        fun setNextButtonState(enabled: Boolean)
        fun setCount(count: Int)
        fun exitCartScreen()
    }

    interface Presenter {
        val page: PageUiModel
        fun loadInitCartProduct()
        fun deleteCartProduct(cartId: Long)
        fun loadPreviousPage()
        fun loadNextPage()
        fun setPage(newPage: PageUiModel)
        fun exit()
    }
}
