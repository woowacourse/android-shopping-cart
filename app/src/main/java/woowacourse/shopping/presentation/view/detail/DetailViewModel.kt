package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toCartItem

class DetailViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _saveState = MutableLiveData<Unit>()
    val saveState: LiveData<Unit> = _saveState

    private val _amount = MutableLiveData<Int>(1)
    val amount: LiveData<Int> = _amount

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

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
        repository.addCartItem(newProduct.toCartItem()) {
            _saveState.postValue(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val repository = RepositoryProvider.cartRepository
                    return DetailViewModel(repository) as T
                }
            }
    }
}
