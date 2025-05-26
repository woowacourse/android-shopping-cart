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

    private val _lastViewed = MutableLiveData<ProductUiModel?>()
    val lastViewed: LiveData<ProductUiModel?> = _lastViewed

    fun setProduct(
        product: ProductUiModel,
        onInserted: () -> Unit = {},
    ) {
        _product.value = product.copy(quantity = 1)
        viewedRepository.insertViewedItem(product) { onInserted() }
    }

    fun addToCart() {
        val item = _product.value ?: return
        if (item.quantity <= 0) return

        repository.findCartItem(item) { exist ->
            val updated =
                exist?.let {
                    it.copy(quantity = it.quantity + item.quantity).toUiModel()
                } ?: item

            val callback = { _uiState.postValue(CartUiState.SUCCESS) }

            if (exist != null) {
                repository.updateCartItem(updated, callback)
            } else {
                repository.insertCartItem(updated, callback)
            }
        }
    }

    fun loadLastViewedItem(currentProductId: Long) {
        viewedRepository.getLastViewedItem { lastViewedItem ->
            val filtered = if (lastViewedItem?.id == currentProductId) null else lastViewedItem
            _lastViewed.postValue(filtered)
        }
    }

    fun increaseQuantity() = updateQuantity(+1)

    fun decreaseQuantity() = updateQuantity(-1)

    private fun updateQuantity(diff: Int) {
        val current = _product.value ?: return
        val newQty = (current.quantity + diff).coerceAtLeast(0)
        _product.postValue(current.copy(quantity = newQty))
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
                    throw IllegalArgumentException("알 수 없는 ViewModel 클래스입니다.$modelClass")
                }
            }
    }
}
