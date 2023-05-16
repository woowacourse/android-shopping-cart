package woowacourse.shopping.cart

import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    override fun setOnClickRemove(): (CartProductUIModel, Int) -> Unit =
        { cartProductUIModel: CartProductUIModel, position: Int ->
            cartRepository.remove(cartProductUIModel)
            view.removeAdapterData(cartProductUIModel, position)
        }
}
