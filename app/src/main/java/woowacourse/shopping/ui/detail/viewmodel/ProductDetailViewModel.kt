package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.ui.utils.Event

class ProductDetailViewModel(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
) : ViewModel() {
    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> get() = _error

    private val _errorMsg: MutableLiveData<Event<String>> = MutableLiveData(Event(""))
    val errorMsg: LiveData<Event<String>> get() = _errorMsg

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _count: MutableLiveData<Int> = MutableLiveData(DEFAULT_COUNT)
    val count: LiveData<Int> get() = _count

    fun loadProduct(productId: Long) {
        runCatching {
            productDao.find(productId)
        }.onSuccess {
            _error.value = false
            _product.value = it
        }.onFailure {
            _error.value = true
            _errorMsg.setErrorHandled(it.message.toString())
        }
    }

    fun addProductToCart() {
        _product.value?.let {
            cartDao.save(Cart(product = it, count = _count.value ?: DEFAULT_COUNT))
        }
    }

    fun plusCount() {
        _count.value?.let {
            _count.value = it + OFFSET
        }
    }

    fun minusCount() {
        _count.value?.let {
            _count.value = (it - OFFSET).coerceAtLeast(DEFAULT_COUNT)
        }
    }

    private fun <T> MutableLiveData<Event<T>>.setErrorHandled(value: T?) {
        if (this.value?.hasBeenHandled == false) {
            value?.let { this.value = Event(it) }
        }
    }

    companion object {
        private const val DEFAULT_COUNT = 1
        private const val OFFSET = 1
    }
}
