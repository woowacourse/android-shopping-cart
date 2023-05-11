package woowacourse.shopping.view.cart

import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    private val cartPagination = CartPagination(PAGINATION_SIZE, cartRepository)

    // private val cartProducts = cartRepository.findAll().map { ProductMockRepository.find(it.id) }.map { it.toUiModel() }.toMutableList()
    private val currentCartProducts = cartPagination.nextItems().map { ProductMockRepository.find(it.id) }.map { it.toUiModel() }.toMutableList()
    private var undoCartProducts = emptyList<ProductModel>()
    override fun fetchProducts() {
        view.showProducts(currentCartProducts)
    }

    override fun removeProduct(id: Int) {
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        view.notifyRemoveItem(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
    }

    override fun showMoreProducts() {
        val mark = currentCartProducts.size
        undoCartProducts = currentCartProducts.toList()
        currentCartProducts.clear()
        currentCartProducts.addAll(cartPagination.nextItems().map { ProductMockRepository.find(it.id) }.map { it.toUiModel() })
        view.notifyAddProducts(mark, PAGINATION_SIZE)
    }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
