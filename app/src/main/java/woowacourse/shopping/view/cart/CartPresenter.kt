package woowacourse.shopping.view.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : CartContract.Presenter {
    private val cartPagination = CartPagination(PAGINATION_SIZE, cartRepository)

    private val currentCartProducts = convertIdToProductModel(cartPagination.nextItems()).toMutableList()

    override fun fetchProducts() {
        view.showProducts(currentCartProducts, cartPagination.isUndoItemsEnabled, cartPagination.isNextItemsEnabled, cartPagination.mark / PAGINATION_SIZE + 1)
    }

    override fun removeProduct(id: Int) {
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        view.notifyRemoveItem(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
    }

    override fun fetchNextPage() {
        val getItems = cartPagination.nextItems()
        if (getItems.isNotEmpty()) {
            currentCartProducts.clear()
            currentCartProducts.addAll(convertIdToProductModel(getItems))
            fetchProducts()
        }
    }

    override fun fetchPrevPage() {
        val getItems = cartPagination.prevItems()
        if (getItems.isNotEmpty()) {
            currentCartProducts.clear()
            currentCartProducts.addAll(convertIdToProductModel(getItems))
            fetchProducts()
        }
    }

    private fun convertIdToProductModel(cartProducts: List<CartProduct>) = cartProducts.map { productRepository.find(it.id) }.map { it.toUiModel() }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
