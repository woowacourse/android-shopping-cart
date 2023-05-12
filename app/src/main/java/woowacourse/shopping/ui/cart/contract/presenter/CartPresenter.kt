package woowacourse.shopping.ui.cart.contract.presenter

import com.example.domain.model.CartRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartItemType
import woowacourse.shopping.ui.cart.contract.CartContract

class CartPresenter(
    val view: CartContract.View,
    val repository: CartRepository,
    offset: Int = 0
) : CartContract.Presenter {
    private var offset = offset
        set(value) {
            when {
                value < 0 -> field = 0
                value > repository.getAll().size -> field = repository.getAll().size
                else -> field = value
            }
        }

    override fun setUpCarts() {
        view.setCarts(
            repository.getSubList(offset, STEP).map { CartItemType.Cart(it.toUIModel()) },
            CartUIModel(
                offset + STEP < repository.getAll().size,
                0 < offset,
                offset / STEP + 1
            )
        )
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
        if (offset == repository.getAll().size) {
            offset -= STEP
        }
        setUpCarts()
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        view.navigateToItemDetail(product)
    }

    override fun getOffset(): Int {
        return offset
    }

    companion object {
        private const val STEP = 5
    }
}
