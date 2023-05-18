package woowacourse.shopping.view.cart

import woowacourse.shopping.domain.CartPagination
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.model.CartProductModel
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
        val nextItemExist = cartPagination.isNextEnabled
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        cartItems.removeAt(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)

        // 남은 자리 페이지 뒷 상품으로 다시 채우기
        if (nextItemExist) {
            cartItems.removeLast()
            fillProductInBlank()
            cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
            view.showChangedItems()
        }
    }
    private fun fillProductInBlank() {
        val product = cartPagination.currentLastItem() ?: return
        val productModel = product.toUiModel(productRepository.find(product.id))
        currentCartProducts.add(productModel)
        cartItems.add(CartViewItem.CartProductItem(productModel))
    }

    override fun fetchNextPage() {
        val items = cartPagination.nextItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    override fun fetchPrevPage() {
        val items = cartPagination.prevItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        cartRepository.update(id, count)
        currentCartProducts.find { it.id == id }?.let {
            val index = currentCartProducts.indexOf(it)
            currentCartProducts[index] = CartProductModel(it.id, it.name, it.imageUrl, count, it.totalPrice / it.count * count)
            cartItems[index] = CartViewItem.CartProductItem(currentCartProducts[index])
            view.showChangedItem(index)
        }
    }

    private fun convertToCartProductModels(cartProducts: List<CartProduct>) =
        cartProducts.map { it.toUiModel(productRepository.find(it.id)) }

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
