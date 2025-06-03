package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.util.SingleLiveEvent
import woowacourse.shopping.data.repository.LastProductRepository
import android.util.Log

class ProductDetailViewModel(
    private val productsRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val lastProductRepository: LastProductRepository,
) : ViewModel() {

    private val _product = MutableLiveData(Product.DEFAULT_PRODUCT)
    val product: LiveData<Product> get() = _product

    private val _count = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    private val _onProductAddedEvent = SingleLiveEvent<Unit>()
    val onProductAddedEvent: LiveData<Unit> get() = _onProductAddedEvent

    private val _onFinishEvent = SingleLiveEvent<Unit>()
    val onFinishEvent: LiveData<Unit> get() = _onFinishEvent

    private val _latestProduct = MutableLiveData<Product?>()
    val latestProduct: LiveData<Product?> get() = _latestProduct

    private val _isEqualProduct = MutableLiveData(false)
    val isEqualProduct: LiveData<Boolean> get() = _isEqualProduct

    fun loadLatestViewedProduct() {
        lastProductRepository.fetchLatestProduct { product ->
            _latestProduct.postValue(product)
        }
    }

    fun changeIsEqualProduct() {
        _isEqualProduct.postValue(_product.value?.id == _latestProduct.value?.id)

    }

    fun addLastProduct() {
        product.value?.let {
            lastProductRepository.insertProduct(it)
        }
    }

    fun updateProductDetail(id: Int) {
        productsRepository.fetchProductDetail(id) { result ->
            _product.postValue(result ?: Product.DEFAULT_PRODUCT)
            loadLatestViewedProduct()
        }
    }

    fun addCartProduct() {
        val product = product.value ?: return
        val count = count.value ?: return


        cartRepository.upsertCartProduct(product, count)
        _onProductAddedEvent.postValue(Unit)
        _onFinishEvent.postValue(Unit)

    }

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        val newValue = (_count.value ?: 0) - 1
        _count.value = if (newValue < 1) 1 else newValue
    }
}