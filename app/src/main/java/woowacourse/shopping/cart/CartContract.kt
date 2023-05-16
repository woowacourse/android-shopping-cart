package woowacourse.shopping.cart

import woowacourse.shopping.uimodel.CartProductUIModel

interface CartContract {
    interface View {
        var presenter: Presenter
        fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int)
    }

    interface Presenter {
        fun setOnClickRemove(): (CartProductUIModel, Int) -> Unit
    }
}
