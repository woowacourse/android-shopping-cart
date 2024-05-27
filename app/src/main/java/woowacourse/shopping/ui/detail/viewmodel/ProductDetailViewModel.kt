package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.CountButtonClickListener
import woowacourse.shopping.ui.utils.Event
import woowacourse.shopping.ui.utils.MutableSingleLiveData
import woowacourse.shopping.ui.utils.SingleLiveData

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), CountButtonClickListener {
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

    private val _addCartComplete = MutableSingleLiveData<Unit>()
    val addCartComplete: SingleLiveData<Unit> get() = _addCartComplete

    fun loadProduct(productId: Long) {
        runCatching {
            productRepository.find(productId)
        }.onSuccess {
            _error.value = false
            _productWithQuantity.value = ProductWithQuantity(product = it)
        }.onFailure {
            _error.value = true
            _errorMsg.setErrorHandled(it.message.toString())
        }
    }

    override fun plusCount(productId: Long) {
        _productWithQuantity.value?.let {
            _productWithQuantity.value = it.inc()
        }
    }

    override fun minusCount(productId: Long) {
        _productWithQuantity.value?.let {
            _productWithQuantity.value = it.dec()
        }
    }

    fun addProductToCart() {
        _productWithQuantity.value?.let { productWithQuantity ->
            repeat(productWithQuantity.quantity.value) {
                cartRepository.plusQuantityByProductId(productWithQuantity.product.id)
            }
            loadProduct(productWithQuantity.product.id)
        }
        _addCartComplete.setValue(Unit)
    }

    fun addToRecentProduct(
        productId: Long,
        lastSeenProductState: Boolean,
    ) {
        loadMostRecentProduct(productId, lastSeenProductState)
        recentProductRepository.insert(productId)
    }

    private fun loadMostRecentProduct(
        productId: Long,
        lastSeenProductState: Boolean,
    ) {
        recentProductRepository.findMostRecentProduct()?.let {
            runCatching {
                productRepository.find(it.productId)
            }.onSuccess {
                _error.value = false
                _mostRecentProduct.value = it
                if (!lastSeenProductState) return
                setMostRecentVisibility(it.id, productId)
            }.onFailure {
                _error.value = true
                _mostRecentProductVisibility.value = false
                _errorMsg.setErrorHandled(it.message.toString())
            }
        }
    }

    private fun setMostRecentVisibility(
        mostRecentProductId: Long,
        currentProductId: Long,
    ) {
        _mostRecentProductVisibility.value = (mostRecentProductId != currentProductId)
    }

    private fun <T> MutableLiveData<Event<T>>.setErrorHandled(value: T?) {
        if (this.value?.hasBeenHandled == false) {
            value?.let { this.value = Event(it) }
        }
    }
}
