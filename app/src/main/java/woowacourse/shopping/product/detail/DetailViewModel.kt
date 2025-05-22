package woowacourse.shopping.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.cart.CartItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailViewModel(
    private val repository: CartItemRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> = _uiState

    fun addToCart(product: ProductUiModel) {
        viewModelScope.launch {
            repository.insertCartItem(product)
        }
    }

    fun setProduct(product: ProductUiModel) {
        _product.value = product
    }

    fun increaseQuantity() {
        viewModelScope.launch {
            _product.value?.let { currentProduct ->
                val newProduct = currentProduct.copy(quantity = currentProduct.quantity + 1)
                _product.value = newProduct
                repository.insertCartItem(newProduct)
            }
        }
    }

    fun decreaseQuantity() {
        viewModelScope.launch {
            _product.value?.let { currentProduct ->
                if (currentProduct.quantity > 1) {
                    val newProduct = currentProduct.copy(quantity = currentProduct.quantity - 1)
                    _product.value = newProduct
                    repository.insertCartItem(newProduct)
                } else {
                    repository.deleteCartItem(currentProduct)
                    _product.value = null
                }
            }
        }
    }

    companion object {
        fun factory(repository: CartItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                        return DetailViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
