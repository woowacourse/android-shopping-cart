package woowacourse.shopping.cart.contract.presenter

import com.domain.model.CartRepository
import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartNavigationUIModel
import woowacourse.shopping.model.CartProductUIModel

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
            CartNavigationUIModel(
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
        var price = 0
        repository.remove(id)
        if (offset == repository.getAll().size) {
            offset -= STEP
        }
        val checkedProducts = repository.getChecked()
        checkedProducts.forEach {
            price += it.count * it.product.price
        }
        view.updatePrice(price)
        view.updateOrderCount(checkedProducts.size)
        setUpCarts()
    }

    override fun navigateToItemDetail(cartProduct: CartProductUIModel) {
        view.navigateToItemDetail(cartProduct)
    }

    override fun getOffset(): Int {
        return offset
    }

    override fun increaseCount(count: Int, cartProduct: CartProductUIModel) {
        repository.updateCount(cartProduct.product.id, count + 1)
    }

    override fun decreaseCount(count: Int, cartProduct: CartProductUIModel) {
        repository.updateCount(cartProduct.product.id, count - 1)
    }

    override fun updateChecked(checked: Boolean, cartProduct: CartProductUIModel) {
        var price = 0
        repository.updateChecked(cartProduct.product.id, checked)
        val checkedProducts = repository.getChecked()
        checkedProducts.forEach {
            price += it.count * it.product.price
        }
        view.updatePrice(price)
        view.updateOrderCount(checkedProducts.size)
    }

    override fun updateTotalChecked(checked: Boolean) {
        val products = repository.getSubList(offset, STEP)
        products.forEach {
            repository.updateChecked(it.product.id, checked)
        }
        setUpCarts()
    }

    companion object {
        private const val STEP = 5
    }
}
