package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.RepositoryProvider
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.detail.event.DetailMessageEvent

class DetailViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<DetailMessageEvent>()
    val toastEvent: SingleLiveData<DetailMessageEvent> = _toastEvent

    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _quantity = MutableLiveData(DEFAULT_QUANTITY)
    val quantity: LiveData<Int> = _quantity

    private val _addToCartSuccessEvent = MutableSingleLiveData<Unit>()
    val addToCartSuccessEvent: SingleLiveData<Unit> = _addToCartSuccessEvent

    fun fetchProduct(productId: Long) {
        if (_product.value != null) return

        repository
            .findProductInfoById(productId)
            .onSuccess { _product.postValue(it.toUiModel()) }
            .onFailure { _toastEvent.postValue(DetailMessageEvent.FETCH_PRODUCT_FAILURE) }
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

        repository.addCartItem(product.id, quantity) { result ->
            result
                .onSuccess { _addToCartSuccessEvent.postValue(Unit) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.ADD_PRODUCT_FAILURE) }
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
                    val repository = RepositoryProvider.shoppingRepository
                    return DetailViewModel(repository) as T
                }
            }
    }
}
