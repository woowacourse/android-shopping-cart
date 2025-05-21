package woowacourse.shopping.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailViewModel(
    private val dataSource: CartProductDataSource,
) : ViewModel() {
    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> = _uiState

    fun addToCart(product: ProductUiModel) {
        val result = dataSource.insertCartProduct(product)

        if (result == CartUiState.SUCCESS) {
            _uiState.value = CartUiState.SUCCESS
        } else {
            _uiState.value = CartUiState.FAIL
        }
    }

    companion object {
        fun factory(dataSource: CartProductDataSource): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                        return DetailViewModel(dataSource) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
