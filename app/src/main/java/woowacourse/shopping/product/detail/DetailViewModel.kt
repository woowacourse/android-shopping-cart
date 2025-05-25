package woowacourse.shopping.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartItemRepository
import woowacourse.shopping.data.recent.ViewedItemRepository
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailViewModel(
    private val repository: CartItemRepository,
    private val viewedRepository: ViewedItemRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> = _uiState

    fun addToCart() {
        val currentProduct = _product.value ?: return
        if (currentProduct.quantity <= 0) return

        repository.findCartItem(currentProduct) { existItem ->
            if (existItem != null) {
                val totalQuantity = existItem.quantity + currentProduct.quantity
                val updated = existItem.copy(quantity = totalQuantity)
                repository.updateCartItem(updated.toUiModel()) {
                    _uiState.postValue(CartUiState.SUCCESS)
                }
            } else {
                repository.insertCartItem(currentProduct) {
                    _uiState.postValue(CartUiState.SUCCESS)
                }
            }
        }
    }

    fun setProduct(
        product: ProductUiModel,
        callback: () -> Unit = {},
    ) {
        _product.value = product.copy(quantity = 1)

        viewedRepository.insertViewedItem(product) {
            callback()
        }
    }

    fun increaseQuantity() {
        val currentProduct = _product.value ?: return
        val newProduct = currentProduct.copy(quantity = currentProduct.quantity + 1)
        _product.postValue(newProduct)
    }

    fun decreaseQuantity() {
        val currentProduct = _product.value ?: return
        val newQuantity = if (currentProduct.quantity > 1) currentProduct.quantity - 1 else 0
        val newProduct = currentProduct.copy(quantity = newQuantity)
        _product.postValue(newProduct)
    }

    companion object {
        fun factory(
            repository: CartItemRepository,
            viewedRepository: ViewedItemRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                        return DetailViewModel(repository, viewedRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
