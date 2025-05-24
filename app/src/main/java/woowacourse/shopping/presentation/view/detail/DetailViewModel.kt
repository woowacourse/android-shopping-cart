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
import woowacourse.shopping.presentation.model.toCartItem
import woowacourse.shopping.presentation.model.toUiModel

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _saveState = MutableLiveData<Unit>()
    val saveState: LiveData<Unit> = _saveState

    private val _amount = MutableLiveData<Int>(1)
    val amount: LiveData<Int> = _amount

    private val _lastViewedProduct = MutableLiveData<ProductUiModel>()
    val lastViewedProduct: LiveData<ProductUiModel> = _lastViewedProduct

    fun decreaseAmount() {
        val current = _amount.value ?: 1
        if (current > 1) {
            _amount.value = current - 1
        }
    }

    fun increaseAmount() {
        val current = _amount.value ?: 1
        _amount.value = current + 1
    }

    fun addProduct(product: ProductUiModel) {
        val newProduct = product.copy(amount = amount.value ?: 0)
        cartRepository.addCartItem(newProduct.toCartItem()) {
            _saveState.postValue(Unit)
        }
    }

    fun fetchLastViewedProduct(currentProductId: Long) {
        productRepository.loadLastViewedProduct(currentProductId) {
            if (it != null) {
                _lastViewedProduct.postValue(it.toUiModel())
            }
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
