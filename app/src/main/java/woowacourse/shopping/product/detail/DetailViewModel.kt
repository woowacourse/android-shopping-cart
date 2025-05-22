package woowacourse.shopping.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.cart.CartItemRepository
import woowacourse.shopping.data.CartItem
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class DetailViewModel(
    private val repository: CartItemRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> = _uiState

    fun addToCart() {
        thread {
            val currentProduct = _product.value ?: return@thread
            if (currentProduct.quantity <= 0) return@thread

            val existItem = repository.findCartItem(currentProduct)
            if (existItem != null) {
                val updated: CartItem = existItem.copy(quantity = currentProduct.quantity)
                repository.updateCartItem(updated.toUiModel())
            } else {
                repository.insertCartItem(currentProduct)
            }

            _uiState.postValue(CartUiState.SUCCESS)
        }
    }

    fun setProduct(product: ProductUiModel) {
        _product.value = product
    }

    fun increaseQuantity() {
        thread {
            _product.value?.let { currentProduct ->
                val newProduct = currentProduct.copy(quantity = currentProduct.quantity + 1)
                _product.postValue(newProduct)
            }
        }
    }

    fun decreaseQuantity() {
        thread {
            _product.value?.let { currentProduct ->
                if (currentProduct.quantity > 1) {
                    val newProduct = currentProduct.copy(quantity = currentProduct.quantity - 1)
                    _product.postValue(newProduct)
                } else if (currentProduct.quantity == 1) {
                    _product.postValue(currentProduct.copy(quantity = 0))
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
