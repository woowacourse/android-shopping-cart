package woowacourse.shopping.view.cart

import android.util.Log
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : CartContract.Presenter {
    private val cartPagination = CartPagination(PAGINATION_SIZE, cartRepository)

    private val currentCartProducts =
        convertIdToModel(cartPagination.nextItems()).toMutableList()

    override fun fetchProducts() {
        Log.d("test", "tesg")
        view.showProducts(
            currentCartProducts,
            cartPagination.isUndoItemsEnabled,
            cartPagination.isNextItemsEnabled,
            cartPagination.getPageNumber(),
        )
    }

    override fun removeProduct(id: Int) {
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        view.notifyRemoveItem(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
        val getItems = cartPagination.currentItems()
        updateCartItems(getItems)
    }

    override fun fetchNextPage() {
        val getItems = cartPagination.nextItems()
        updateCartItems(getItems)
    }

    override fun fetchUndoPage() {
        val getItems = cartPagination.undoItems()
        updateCartItems(getItems)
    }

    private fun updateCartItems(getItems: List<CartProduct>) {
        if (getItems.isNotEmpty()) {
            currentCartProducts.clear()
            currentCartProducts.addAll(convertIdToModel(getItems))
            fetchProducts()
        }
    }

    // private fun convertIdToProductModel(cartProducts: List<CartProduct>) =
    //  cartProducts.map { productRepository.find(it.id) }.map { it.toUiModel() }

    private fun convertIdToModel(cartProducts: List<CartProduct>): List<CartProductModel> {
        return cartProducts.map { cartProduct ->
            val product = productRepository.find(cartProduct.id)
            val productModel = product.toUiModel()
            CartProductModel(cartProduct.id, productModel, cartProduct.count, cartProduct.check)
        }
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            android.R.id.home -> {
                view.handleBackButtonClicked()
            }
        }
    }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
