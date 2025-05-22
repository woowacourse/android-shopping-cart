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
import woowacourse.shopping.domain.CartItem

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<CartItem>> = MutableLiveData(emptyList())
    val products: LiveData<List<CartItem>> get() = _products

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _hasPrevious: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasPrevious: LiveData<Boolean> get() = _hasPrevious

    private val _hasNext: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasNext: LiveData<Boolean> get() = _hasNext

    private var totalProductsCount = 0
    private var totalPage = 0
    private var offset = 0
    private val pageCache: MutableMap<Int, List<CartItem>> = mutableMapOf()

    init {
        fetchInfo()
        fetchData()
    }

    fun fetchInfo() {
        cartRepository.getAllProductsSize {
            totalProductsCount = it
            totalPage = calculateTotalPages(totalProductsCount)
            hasPrevious()
            hasNext()
        }
    }

    fun fetchData() {
        if (pageCache[_currentPage.value] == null) {
            cartRepository.getProducts(limit = LIMIT_COUNT, offset) { products ->
                _products.postValue(products)
                pageCache.put(_currentPage.value ?: 1, products)
            }
            offset += LIMIT_COUNT
        } else {
            _products.postValue(pageCache[_currentPage.value])
        }
    }

    fun deleteProduct(cartItem: CartItem) {
        cartRepository.deleteProduct(cartItem.cartItemId)
        val currentProducts =
            _products.value
                ?.filterNot { it == cartItem } ?: emptyList()

        if (_currentPage.value != totalPage) {
            val currentPage = _currentPage.value ?: 1
            offset = currentPage * LIMIT_COUNT
            cartRepository.getProducts(1, offset) { product ->
                val newProduct = currentProducts + product
                _products.postValue(newProduct)
                pageCache[_currentPage.value ?: 1] = newProduct
            }
            for (page in currentPage + 1..totalPage) {
                pageCache.remove(page)
            }
            totalProductsCount -= 1
            totalPage = calculateTotalPages(totalProductsCount)
            if (totalPage == _currentPage.value) {
                _currentPage.postValue(totalPage)
            }
        } else if (_currentPage.value == totalPage && pageCache[totalPage]?.size == 1) {
            totalProductsCount -= 1
            totalPage = calculateTotalPages(totalProductsCount)
            minusPage()
            fetchData()
        } else {
            _products.value = currentProducts
            pageCache[_currentPage.value ?: 1] = currentProducts
            totalProductsCount -= 1
            totalPage = calculateTotalPages(totalProductsCount)
        }
    }

    fun hasNext() {
        if (_currentPage.value?.let { it < totalPage } == true) {
            _hasNext.postValue(true)
        } else {
            _hasNext.postValue(false)
        }
    }

    fun hasPrevious() {
        if (_currentPage.value?.let { it > 1 } == true) {
            _hasPrevious.postValue(true)
        } else {
            _hasPrevious.postValue(false)
        }
    }

    fun minusPage() {
        _currentPage.value?.let {
            if (it > 1) {
                _currentPage.value = (_currentPage.value ?: 1) - 1
            }
        }
    }

    fun plusPage() {
        _currentPage.value = (_currentPage.value ?: 1) + 1
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
