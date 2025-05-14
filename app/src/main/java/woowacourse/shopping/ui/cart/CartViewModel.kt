package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartDummyRepositoryImpl
import woowacourse.shopping.domain.model.Product

class CartViewModel : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> get() = _products

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(1)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _maxPageCount: MutableLiveData<Int> = MutableLiveData<Int>(1)
    val maxPageCount: LiveData<Int> get() = _maxPageCount

    val cartDummyRepository = CartDummyRepositoryImpl

    init {
        updateCartProducts()
        updateMaxPageCount()
    }

    fun updateCartProducts() {
        _products.value = cartDummyRepository.fetchCartProducts(currentPage.value ?: 1)
    }

    fun removeCartProduct(id: Int) {
        cartDummyRepository.removeCartProduct(id)
        _products.value = products.value?.filter { it.id != id }
    }

    fun increasePageCount() {
        _currentPage.value = currentPage.value?.plus(1)
    }

    fun decreasePageCount() {
        _currentPage.value = currentPage.value?.minus(1)
    }

    fun updateMaxPageCount() {
        _maxPageCount.value = cartDummyRepository.fetchMaxPageCount()
    }
}
