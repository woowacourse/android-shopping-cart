package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    val canLoadPrevPage: LiveData<Boolean>
        get() = MediatorLiveData<Boolean>().apply {
            addSource(currentPage) {
                val prevPage = it - INCREMENT_AMOUNT
                value = canLoadMoreCartProducts(prevPage)
            }
            addSource(products) {
                val currentPage = currentPage.value ?: return@addSource
                value = canLoadMoreCartProducts(currentPage)
            }
        }
    val canLoadNextPage: LiveData<Boolean>
        get() = MediatorLiveData<Boolean>().apply {
            addSource(currentPage) {
                val nextPage = it - INCREMENT_AMOUNT
                value = canLoadMoreCartProducts(nextPage)
            }
            addSource(products) {
                val currentPage = currentPage.value ?: return@addSource
                value = canLoadMoreCartProducts(currentPage)
            }
        }

    init {
        loadCartProducts(START_PAGE)
    }

    private fun loadCartProducts(page: Int) {
        _products.value =
            cartRepository.cartProducts(page, PAGE_SIZE).map { it.toUiModel() }
    }

    private fun canLoadMoreCartProducts(currentPage: Int): Boolean {
        return cartRepository.canLoadMoreCartProducts(currentPage, PAGE_SIZE)
    }

    fun deleteProduct(position: Int) {
        val deletedItem = _products.value?.get(position) ?: return
        _products.value = _products.value?.minus(deletedItem)
        cartRepository.deleteCartProduct(deletedItem.product.id) ?: return
        val currentPage = currentPage.value ?: return
        loadCartProducts(currentPage)
    }

    fun plusPage() {
        val currentPage = _currentPage.value ?: return
        loadCartProducts(currentPage + INCREMENT_AMOUNT)
        _currentPage.value = _currentPage.value?.plus(INCREMENT_AMOUNT)
    }

    fun minusPage() {
        val currentPage = _currentPage.value ?: return
        loadCartProducts(currentPage - INCREMENT_AMOUNT)
        _currentPage.value = _currentPage.value?.minus(INCREMENT_AMOUNT)
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val PAGE_SIZE = 5
        private const val INCREMENT_AMOUNT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}
