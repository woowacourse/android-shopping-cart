package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel
import woowacourse.shopping.model.ProductUiModel

interface CartContract {
    interface View {
        fun changeCartProducts(newItems: List<CartProductUiModel>)
        fun setPageState(hasPrevious: Boolean, hasNext: Boolean, pageNumber: Int)
    }

    interface Presenter {
        val page: PageUiModel
        fun setup()
        fun deleteCartProduct(product: ProductUiModel)
        fun loadPreviousPage()
        fun loadNextPage()
        fun setPage(page: PageUiModel)
        fun increaseCartProduct(product: ProductUiModel, previousCount: Int)
        fun decreaseCartProduct(product: ProductUiModel, previousCount: Int)
        fun toggleCartProduct(cartProduct: CartProductUiModel, isSelected: Boolean)
        fun toggleAllProductOnPage(isSelected: Boolean)
    }
}
