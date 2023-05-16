package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductUiModel>)
        fun setPageState(hasPrevious: Boolean, hasNext: Boolean, pageNumber: Int)
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
