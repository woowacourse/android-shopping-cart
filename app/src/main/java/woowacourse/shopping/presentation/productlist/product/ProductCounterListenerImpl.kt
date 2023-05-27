package woowacourse.shopping.presentation.productlist.product

import woowacourse.shopping.presentation.common.CounterListener
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.productlist.ProductListContract

class ProductCounterListenerImpl constructor(
    private val cartProductInfoModel: CartProductInfoModel,
    private val presenter: ProductListContract.Presenter,
) : CounterListener {

    override fun onPlus(count: Int) {
        presenter.updateCartProductCount(cartProductInfoModel, count)
        presenter.updateCartCount()
        presenter.refreshProductItems()
    }

    override fun onMinus(count: Int) {
        presenter.updateCartProductCount(cartProductInfoModel, count)
        presenter.updateCartCount()
        presenter.refreshProductItems()
    }
}
