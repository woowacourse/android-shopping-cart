package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.db.cart.CartRepository
import woowacourse.shopping.model.db.recentproduct.RecentProductRepository
import woowacourse.shopping.ui.utils.Event
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val productDao: ProductDao,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
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

    private val _mostRecentProduct: MutableLiveData<Product> = MutableLiveData()
    val mostRecentProduct: LiveData<Product> get() = _mostRecentProduct

    private val _mostRecentProductVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val mostRecentProductVisibility: MutableLiveData<Boolean> get() = _mostRecentProductVisibility

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
                thread {
                    cartRepository.plusQuantityByProductId(productWithQuantity.product.id)
                }.join()
            }
            loadProduct(productWithQuantity.product.id)
        }
    }

    fun addToRecentProduct(
        productId: Long,
        lastSeenProductState: Boolean,
    ) {
        loadMostRecentProduct(productId, lastSeenProductState)
        thread {
            recentProductRepository.insert(productId)
        }.join()
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

    private fun loadMostRecentProduct(
        productId: Long,
        lastSeenProductState: Boolean,
    ) {
        thread {
            recentProductRepository.findMostRecentProduct()?.let {
                runCatching {
                    productDao.find(it.productId)
                }.onSuccess {
                    _error.postValue(false)
                    _mostRecentProduct.postValue(it)
                    if (!lastSeenProductState) return@thread
                    setMostRecentVisibility(it.id, productId)
                }.onFailure {
                    _error.postValue(true)
                    _mostRecentProductVisibility.postValue(false)
                    _errorMsg.setErrorHandled(it.message.toString())
                }
            }
        }.join()
    }

    private fun setMostRecentVisibility(
        mostRecentProductId: Long,
        currentProductId: Long,
    ) {
        if (mostRecentProductId == currentProductId) {
            _mostRecentProductVisibility.postValue(false)
            return
        }
        _mostRecentProductVisibility.postValue(true)
    }

    private fun <T> MutableLiveData<Event<T>>.setErrorHandled(value: T?) {
        if (this.value?.hasBeenHandled == false) {
            value?.let { this.postValue(Event(it)) }
        }
    }
}
