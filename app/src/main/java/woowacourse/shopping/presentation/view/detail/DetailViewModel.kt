package woowacourse.shopping.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toProduct

class DetailViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _saveState = MutableLiveData<Unit>()
    val saveState: LiveData<Unit> = _saveState

    fun addProduct(product: ProductUiModel) {
        repository.addCartItem(product.toProduct()) {
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
