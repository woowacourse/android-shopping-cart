package woowacourse.shopping.feature.product.detail.dialog

import woowacourse.shopping.data.repository.cart.source.local.CartLocalDataSourceImpl
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toProductDomain

class CounterDialogPresenter(
    private val view: CounterDialogContract.View,
    private val cartDb: CartLocalDataSourceImpl,
    product: CartProductItem,
) : CounterDialogContract.Presenter {
    override val product: CartProductItem = product
    override var count: Int = product.count
        private set

    override fun loadInitialData() {
        view.setCountState(count)
    }

    override fun addCart() {
        cartDb.findProductById(product.id)?.let {
            count += it.count
            cartDb.updateColumn(product.updateCount(count))
        } ?: cartDb.addColumn(product.toProductDomain(), count)
        view.exit()
    }

    override fun updateCount(changeWidth: Int) {
        this.count += changeWidth
        view.setCountState(this.count)
    }
}
