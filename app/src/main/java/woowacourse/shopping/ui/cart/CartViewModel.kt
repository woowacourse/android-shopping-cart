package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class CartViewModel(
    private val cartDummyRepository: CartDummyRepositoryImpl = CartDummyRepositoryImpl,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE_COUNT)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _maxPageCount: MutableLiveData<Int> = MutableLiveData<Int>(INITIAL_PAGE_COUNT)
    val maxPageCount: LiveData<Int> get() = _maxPageCount

    init {
        updateCartProducts()
        updateMaxPageCount()
    }

    fun updateCartProducts() {
        _products.value =
            cartDummyRepository.fetchCartProducts(currentPage.value ?: INITIAL_PAGE_COUNT)
    }

    fun removeCartProduct(id: Int) {
        cartDummyRepository.removeCartProduct(id)
        _products.value = products.value?.filter { it.id != id }
    }

    fun increasePageCount(step: Int = DEFAULT_PAGE_STEP) {
        _currentPage.value = currentPage.value?.plus(step)
    }

    fun decreasePageCount(step: Int = DEFAULT_PAGE_STEP) {
        _currentPage.value = currentPage.value?.minus(step)
    }

    fun updateMaxPageCount() {
        _maxPageCount.value = cartDummyRepository.fetchMaxPageCount()
    }

    companion object {
        const val INITIAL_PAGE_COUNT: Int = 1
        const val DEFAULT_PAGE_STEP: Int = 1
    }
}
