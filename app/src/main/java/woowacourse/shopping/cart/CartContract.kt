package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartContract {
    interface View {
        fun setCartProducts(newCartProducts: List<CartProductUIModel>)
        fun setPage(page: Int)
        fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int)
    }

    interface Presenter {
        fun setOnClickRemove(): (CartProductUIModel, Int) -> Unit
    }
}
