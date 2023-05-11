package woowacourse.shopping.view.cart

import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    private val cartProducts = cartRepository.findAll().map { ProductMockRepository.find(it.id) }.map { it.toUiModel() }.toMutableList()
    override fun fetchProducts() {
        view.showProducts(cartProducts)
    }

    override fun removeProduct(id: Int) {
        val removedItem = cartProducts.find { it.id == id }
        cartRepository.remove(id)
        view.notifyRemoveItem(cartProducts.indexOf(removedItem))
        cartProducts.remove(removedItem)
    }
}
