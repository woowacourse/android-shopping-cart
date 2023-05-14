package woowacourse.shopping.ui.cart

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private var index: Int = 0
) : CartContract.Presenter {

    override fun setUpCarts() {
        view.setCarts(
            cartRepository.getPage(index, STEP).toUIModel().map { CartItemType.Cart(it) },
            PageUIModel(
                cartRepository.hasNextPage(index, STEP),
                cartRepository.hasPrevPage(index, STEP),
                index + 1
            )
        )
    }

    override fun moveToPageNext() {
        index += 1
        setUpCarts()
    }

    override fun moveToPagePrev() {
        index -= 1
        setUpCarts()
    }

    override fun removeItem(id: Int) {
        cartRepository.remove(id)
        if (cartRepository.getPage(index, STEP).toUIModel().isEmpty()) {
            index -= 1
        }
        setUpCarts()
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let { view.navigateToItemDetail(it.toUIModel()) }
    }

    override fun getPageIndex(): Int {
        return index
    }

    companion object {
        private const val STEP = 5
    }
}
