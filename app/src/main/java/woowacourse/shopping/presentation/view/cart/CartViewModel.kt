package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.UiState
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toUiModel

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUiModel>>()
    val products: LiveData<List<ProductUiModel>> = _products

    private val _deleteState = MutableLiveData<UiState<Long>>()
    val deleteState: LiveData<UiState<Long>> = _deleteState

    init {
        fetchShoppingCart()
    }

    private fun fetchShoppingCart() {
        cartRepository.getCartItems { products ->
            _products.postValue(products.map { it.toUiModel() })
        }
    }

    fun deleteProduct(product: ProductUiModel) {
        cartRepository.deleteCartItem(product.id) {
            _deleteState.postValue(UiState.Success(it))
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
                    return CartViewModel(repository) as T
                }
            }
    }
}
