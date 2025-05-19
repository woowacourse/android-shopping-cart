package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository

class CartViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CartProduct>>(emptyList())
    val products: LiveData<List<CartProduct>> get() = _products

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> get() = _pageNumber

    private var size: Int = 0
        private set

    init {
        update()
    }

    fun isLastPage(): Boolean {
        val pageNumber = pageNumber.value ?: 1
        val size = pageNumber * 5
        return this.size <= size
    }

    private fun update() {
        val pageNumber = pageNumber.value ?: 1
        repository.fetchInRange(5, (pageNumber - 1) * 5) { products ->
            _products.postValue(products)
        }
    }

    fun moveToPrevious() {
        if ((_pageNumber.value ?: 1) > 1) {
            _pageNumber.value = _pageNumber.value?.minus(1)
            update()
        }
    }

    fun moveToNext() {
        if (!isLastPage()) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            update()
        }
    }

    fun deleteProduct(cartProduct: CartProduct) {
        repository.delete(cartProduct.id!!)
        _products.value = _products.value?.filter { it.id != cartProduct.id }
    }
}
