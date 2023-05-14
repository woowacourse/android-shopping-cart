package woowacourse.shopping.ui.cart

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType

class CartPresenter(
    val view: CartContract.View,
    val repository: CartRepository,
    val productRepository: ProductRepository,
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
            PageUIModel(
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

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let { view.navigateToItemDetail(it.toUIModel()) }
    }

    override fun getOffset(): Int {
        return offset
    }

    companion object {
        private const val STEP = 5
    }
}
