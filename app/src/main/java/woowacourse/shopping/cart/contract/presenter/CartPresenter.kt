package woowacourse.shopping.cart.contract.presenter

import com.example.domain.model.CartRepository
import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel

class CartPresenter(
    val view: CartContract.View,
    val repository: CartRepository
) : CartContract.Presenter {
    private var offset = 0

    override fun setUpCarts() {
        view.setCarts(repository.getSubList(offset, STEP).map { CartItem(it.toUIModel()) })
    }

    override fun pageUp() {
        offset += STEP
        setUpCarts()
    }

    override fun pageDown() {
        offset -= STEP
        setUpCarts()
    }

    override fun removeItem(id: Int) {
        repository.remove(id)
        setUpCarts()
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        view.navigateToItemDetail(product)
    }

    companion object {
        private const val STEP = 5
    }
}
