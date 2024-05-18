package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CartProductUi>>(emptyList())
    val products: LiveData<List<CartProductUi>> get() = _products

    private val _currentPage = MutableLiveData(START_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _canLoadPrevPage: MutableLiveData<Boolean> = MutableLiveData(false)
    val canLoadPrevPage: LiveData<Boolean> get() = _canLoadPrevPage

    private val _canLoadNextPage: MutableLiveData<Boolean> = MutableLiveData(false)
    val canLoadNextPage: LiveData<Boolean> get() = _canLoadNextPage

    init {
        _products.value = cartRepository.cartProducts(1).map { it.toUiModel() }
        updateEnabledPage()
    }

    fun deleteProduct(position: Int) {
        val deletedItem = _products.value?.get(position)
        if (deletedItem != null) {
            _products.value = _products.value?.minus(deletedItem)
            cartRepository.deleteCartProduct(deletedItem.product.id)
        }
    }

    fun plusPage() {
        val currentPage = _currentPage.value ?: return
        if (cartRepository.canLoadMoreCartProducts(currentPage)) {
            _products.value =
                cartRepository.cartProducts(currentPage + INCREMENT_AMOUNT).map { it.toUiModel() }
            _currentPage.value = _currentPage.value?.plus(INCREMENT_AMOUNT)
        }
        updateEnabledPage()
    }

    fun minusPage() {
        val currentPage = _currentPage.value ?: return

        _products.value =
            cartRepository.cartProducts(currentPage - INCREMENT_AMOUNT).map { it.toUiModel() }
        _currentPage.value = _currentPage.value?.minus(INCREMENT_AMOUNT)
        updateEnabledPage()
    }

    private fun updateEnabledPage() {
        val currentPage = _currentPage.value ?: return
        _canLoadPrevPage.value = currentPage > 1
        _canLoadNextPage.value = cartRepository.canLoadMoreCartProducts(currentPage)
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val INCREMENT_AMOUNT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}
