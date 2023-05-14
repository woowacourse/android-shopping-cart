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

    private val currentCartProducts =
        convertToCartProductModels(cartPagination.nextItems()).toMutableList()
    private val cartItems =
        (
            currentCartProducts.map { CartViewItem.CartProductItem(it) } +
                CartViewItem.PaginationItem(cartPagination.status)
            ).toMutableList()

    override fun fetchProducts() {
        view.showProducts(cartItems)
    }

    override fun removeProduct(id: Int) {
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        view.notifyRemoveItem(currentCartProducts.indexOf(removedItem))
        cartItems.removeAt(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
    }

    override fun fetchNextPage() {
        val items = cartPagination.nextItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showOtherPage()
        }
    }

    override fun fetchPrevPage() {
        val items = cartPagination.prevItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showOtherPage()
        }
    }

    private fun convertToCartProductModels(cartProducts: List<CartProduct>) =
        cartProducts.asSequence().map { it.toUiModel(productRepository.find(it.id)) }.toList()

    private fun changeListItems(items: List<CartProduct>) {
        currentCartProducts.clear()
        currentCartProducts.addAll(convertToCartProductModels(items))
        cartItems.clear()
        cartItems.addAll(currentCartProducts.map { CartViewItem.CartProductItem(it) })
        cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
    }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
