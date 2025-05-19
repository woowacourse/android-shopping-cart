package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.cartRepository.CartRepository
import woowacourse.shopping.uimodel.CartItem

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _totalProductsCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalProductsCount: LiveData<Int> get() = _totalProductsCount

    private val _products: MutableLiveData<List<CartItem>> = MutableLiveData(emptyList())
    val products: LiveData<List<CartItem>> get() = _products

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _hasPrevious: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasPrevious: LiveData<Boolean> get() = _hasPrevious

    private val _hasNext: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasNext: LiveData<Boolean> get() = _hasNext

    private var totalPage = 0

    fun fetchInfo() {
        cartRepository.getAllProductsSize {
            _totalProductsCount.postValue(it)
        }
        _totalProductsCount.value?.let {
            totalPage = calculateTotalPages(it)
        }
        _currentPage.postValue(1)
    }

    fun fetchData() {
        cartRepository.getProducts(limit = LIMIT_COUNT) { products ->
            val currentList = _products.value ?: emptyList()
            val newProducts = currentList + products
            _products.postValue(newProducts)
        }
    }

    fun deleteProduct(cartItem: CartItem) {
        cartRepository.deleteProduct(cartItem.cartItemId)
        _totalProductsCount.value?.minus(1)
        _totalProductsCount.value?.let {
            totalPage = calculateTotalPages(it)
        }
    }

    fun minusPage() {
        _currentPage.value?.minus(1)
    }

    fun plusPage() {
        _currentPage.value?.plus(1)
    }

    private fun calculateTotalPages(totalCount: Int): Int = if (totalCount == 0) 0 else (totalCount - 1) / LIMIT_COUNT + 1

    companion object {
        private const val LIMIT_COUNT = 5

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val cartRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).cartRepository
                    CartViewModel(
                        cartRepository = cartRepository,
                    )
                }
            }
    }
}
