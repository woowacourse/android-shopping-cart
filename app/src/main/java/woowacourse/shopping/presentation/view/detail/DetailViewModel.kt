package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.detail.event.DetailMessageEvent

class DetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<DetailMessageEvent>()
    val toastEvent: SingleLiveData<DetailMessageEvent> = _toastEvent

    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _quantity = MutableLiveData(DEFAULT_QUANTITY)
    val quantity: LiveData<Int> = _quantity

    private val _recentProduct = MutableLiveData<ProductUiModel>()
    val recentProduct: LiveData<ProductUiModel> = _recentProduct

    private val _addToCartSuccessEvent = MutableSingleLiveData<Unit>()
    val addToCartSuccessEvent: SingleLiveData<Unit> = _addToCartSuccessEvent

    init {
        fetchRecentProduct()
    }

    fun fetchProduct(productId: Long) {
        updateRecentProduct(productId)

        shoppingRepository.findProductInfoById(productId) { result ->
            result
                .onSuccess { _product.postValue(it.toUiModel()) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.FETCH_PRODUCT_FAILURE) }
        }
    }

    fun increaseQuantity() {
        val currentQuantity = _quantity.value ?: DEFAULT_QUANTITY
        _quantity.value = currentQuantity + QUANTITY_STEP
    }

    fun decreaseQuantity() {
        val currentQuantity = _quantity.value ?: DEFAULT_QUANTITY
        if (currentQuantity > DEFAULT_QUANTITY) {
            _quantity.value = currentQuantity - QUANTITY_STEP
        }
    }

    fun addProduct() {
        val product = _product.value ?: return
        val quantity = _quantity.value ?: DEFAULT_QUANTITY

        shoppingRepository.addCartItem(product.id, quantity) { result ->
            result
                .onSuccess { _addToCartSuccessEvent.postValue(Unit) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.ADD_PRODUCT_FAILURE) }
        }
    }

    private fun updateRecentProduct(productId: Long) {
        recentProductRepository.insertAndTrimToLimit(productId) { result ->
            result.onFailure { _toastEvent.postValue(DetailMessageEvent.FETCH_PRODUCT_FAILURE) }
        }
    }

    private fun fetchRecentProduct() {
        recentProductRepository.getRecentProducts(1) { result ->
            result
                .onSuccess { _recentProduct.postValue(it.firstOrNull()?.toUiModel()) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.FETCH_PRODUCT_FAILURE) }
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY = 1
        private const val QUANTITY_STEP = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val shoppingRepository = RepositoryProvider.shoppingRepository
                    val recentProductRepository = RepositoryProvider.recentProductRepository
                    return DetailViewModel(shoppingRepository, recentProductRepository) as T
                }
            }
    }
}
