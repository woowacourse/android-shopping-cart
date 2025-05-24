package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData
import woowacourse.shopping.presentation.cart.CartCounterClickListener

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    CartCounterClickListener {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product
    private val _productCount: MutableLiveData<Int> = MutableLiveData(1)
    val productCount: LiveData<Int> = _productCount
    private val _recentProduct: MutableLiveData<Product?> = MutableLiveData()
    val recentProduct: LiveData<Product?> = _recentProduct
    private val _isRecentProduct: MutableLiveData<Boolean> = MutableLiveData()
    val isRecentProduct: LiveData<Boolean> = _isRecentProduct
    private val _insertProductResult: MutableLiveData<ResultState<Unit>> = MutableLiveData()
    val insertProductResult: LiveData<ResultState<Unit>> = _insertProductResult
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    fun fetchData(productId: Long) {
        loadRecentProduct()

        productRepository.fetchProductById(productId) { result ->
            result
                .onSuccess { product ->
                    _product.postValue(product)
                    insertCurrentProductToRecent(product)
                    checkIfCurrentIsMostRecent(product.productId)
                }.onFailure {
                    _toastMessage.postValue(R.string.product_detail_toast_load_fail)
                }
        }
    }

    fun addToCart(productId: Long) {
        val quantity: Int = _productCount.value ?: return
        cartRepository.insertOrIncrease(productId, quantity) { result ->
            result
                .onSuccess { _insertProductResult.postValue(ResultState.Success(Unit)) }
                .onFailure { _insertProductResult.postValue(ResultState.Failure()) }
        }
    }

    override fun onClickMinus(id: Long) {
        val currentCount = _productCount.value ?: return
        if (currentCount == 1) {
            _toastMessage.value = R.string.product_detail_toast_invalid_quantity
            return
        }
        _productCount.value = currentCount - 1
    }

    override fun onClickPlus(id: Long) {
        val currentCount = _productCount.value ?: return
        _productCount.value = currentCount + 1
    }

    private fun insertCurrentProductToRecent(product: Product) {
        recentProductRepository.insertRecentProduct(product) { result ->
            result.onFailure {
                _toastMessage.postValue(R.string.product_detail_toast_most_recent_insert_fail)
            }
        }
    }

    private fun loadRecentProduct() {
        recentProductRepository.getMostRecentProduct { result ->
            result
                .onSuccess { _recentProduct.postValue(it) }
                .onFailure {
                    _toastMessage.postValue(R.string.product_detail_toast_most_recent_load_fail)
                }
        }
    }

    private fun checkIfCurrentIsMostRecent(currentProductId: Long) {
        recentProductRepository.getMostRecentProduct { result ->
            result.onSuccess { recentProduct ->
                val isSame = recentProduct == null || recentProduct.productId == currentProductId
                _isRecentProduct.postValue(isSame)
            }
        }
    }
}
