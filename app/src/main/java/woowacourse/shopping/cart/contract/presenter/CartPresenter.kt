package woowacourse.shopping.cart.contract.presenter

import com.domain.model.CartRepository
import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel

class CartPresenter(
    val view: CartContract.View,
    private val repository: CartRepository,
    offset: Int = 0,
) : CartContract.Presenter {
    private var offset = offset
        set(value) {
            field = value.coerceIn(0, repository.getAll().size)
        }

    override fun setUpCarts() {
        val isNotLastPage = offset + STEP < repository.getAll().size
        val isNotFirstPage = 0 < offset
        val pageNumber = offset / STEP + 1
        view.setCarts(
            repository.getSubList(offset, STEP).map { CartItem(it.toUIModel()) },
            CartUIModel(
                isNotLastPage,
                isNotFirstPage,
                pageNumber,
            ),
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
