package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toProduct
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.detail.event.DetailMessageEvent

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<DetailMessageEvent>()
    val toastEvent: SingleLiveData<DetailMessageEvent> = _toastEvent

    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _saveEvent = MutableSingleLiveData<Unit>()
    val saveEvent: SingleLiveData<Unit> = _saveEvent

    fun fetchProduct(productId: Long) {
        if (_product.value != null) return

        productRepository.findProductById(productId) { result ->
            result
                .onSuccess { _product.postValue(it.toUiModel()) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.FETCH_PRODUCT_FAILURE) }
        }
    }

    fun addProduct() {
        val product = _product.value ?: return

        cartRepository.addCartItem(product.toProduct()) { result ->
            result
                .onSuccess { _saveEvent.postValue(Unit) }
                .onFailure { _toastEvent.postValue(DetailMessageEvent.ADD_PRODUCT_FAILURE) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val cartRepository = RepositoryProvider.cartRepository
                    val productRepository = RepositoryProvider.productRepository
                    return DetailViewModel(cartRepository, productRepository) as T
                }
            }
    }
}
