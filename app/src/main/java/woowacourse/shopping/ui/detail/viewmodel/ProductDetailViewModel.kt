package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.ui.utils.Event

class ProductDetailViewModel(
    private val productDao: ProductDao,
) : ViewModel() {
    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> get() = _error

    private val _errorMsg: MutableLiveData<Event<String>> = MutableLiveData(Event(""))
    val errorMsg: LiveData<Event<String>> get() = _errorMsg

    private val _productWithQuantity: MutableLiveData<ProductWithQuantity> = MutableLiveData()
    val productWithQuantity: LiveData<ProductWithQuantity> get() = _productWithQuantity

    val isInvalidCount: LiveData<Boolean> =
        _productWithQuantity.map {
            it.quantity.value == 0
        }

    fun loadProduct(productId: Long) {
        runCatching {
            productDao.find(productId)
        }.onSuccess {
            _error.value = false
            _productWithQuantity.value = ProductWithQuantity(product = it)
        }.onFailure {
            _error.value = true
            _errorMsg.setErrorHandled(it.message.toString())
        }
    }

    fun addProductToCart() {
        _productWithQuantity.value?.let { productWithQuantity ->
            repeat(productWithQuantity.quantity.value) {
                CartsImpl.plusQuantityByProductId(productWithQuantity.product.id)
            }
            loadProduct(productWithQuantity.product.id)
        }
    }

    fun plusCount() {
        _productWithQuantity.value?.let {
            _productWithQuantity.value = it.inc()
        }
    }

    fun minusCount() {
        _productWithQuantity.value?.let {
            _productWithQuantity.value = it.dec()
        }
    }

    private fun <T> MutableLiveData<Event<T>>.setErrorHandled(value: T?) {
        if (this.value?.hasBeenHandled == false) {
            value?.let { this.value = Event(it) }
        }
    }
}
