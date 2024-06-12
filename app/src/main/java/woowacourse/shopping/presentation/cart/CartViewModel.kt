package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(), CartAction {
    private val _currentPage = MutableLiveData(START_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _products = MutableLiveData<List<CartProductUi>>()
    val products: LiveData<List<CartProductUi>> get() = _products

    val canLoadPrevPage: LiveData<Boolean> =
        _currentPage.map { page ->
            page > 1
        }

    val canLoadNextPage: LiveData<Boolean> =
        _currentPage.map { page -> cartRepository.canLoadMoreCartProducts(page) }

    init {
        loadProducts()
    }

     fun loadProducts(page : Int = START_PAGE) {
        _products.value = cartRepository.cartProducts(page).map { it.toUiModel(true) }
    }

    fun moveToNextPage() {
        val currentPage = _currentPage.value ?: return
        if (cartRepository.canLoadMoreCartProducts(currentPage)) {
            refreshCurrentPage(INCREMENT_AMOUNT)
        }
    }

    fun moveToPreviousPage() {
        val currentPage = _currentPage.value ?: return
        if (currentPage > 1) {
            refreshCurrentPage(-INCREMENT_AMOUNT)
        }
    }

    private fun refreshCurrentPage(increment: Int = 0) {
        val currentPage = _currentPage.value ?: return
        _currentPage.value = currentPage + increment
    }

    override fun deleteProduct(cart: CartProductUi) {
        val currentProducts = products.value ?: return
        if (currentProducts.contains(cart)) {
            cartRepository.deleteCartProduct(cart.product.id)
            refreshCurrentPage()
        }
    }
    override fun onPlus(cartProduct: ShoppingUiModel.Product) {
        val currentProducts = products.value ?: return
        val updatedProducts = currentProducts.map {
            if (it.product.id == cartProduct.id) {
                val updatedProduct = it.copy(product = it.product.copy(count = it.product.count + 1))
                cartRepository.addCartProduct(updatedProduct.product.id, updatedProduct.product.count)
                updatedProduct
            } else {
                it
            }
        }
        _products.value = updatedProducts
    }

    override fun onMinus(cartProduct: ShoppingUiModel.Product) {
        if (cartProduct.count == 1) return
        val currentProducts = products.value ?: return
        val updatedProducts = currentProducts.map {
            if (it.product.id == cartProduct.id) {
                val updatedProduct = it.copy(product = it.product.copy(count = it.product.count - 1))
                cartRepository.addCartProduct(updatedProduct.product.id, updatedProduct.product.count)
                updatedProduct
            } else {
                it
            }
        }
        _products.value = updatedProducts
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val INCREMENT_AMOUNT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}
