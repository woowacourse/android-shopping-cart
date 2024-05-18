package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(private val repository: ShoppingItemsRepository) : ViewModel() {
    private var offset = 0
    private lateinit var productsData: List<Product>

    private val _uiState = MutableLiveData<UIState<List<Product>>>()
    val uiState: LiveData<UIState<List<Product>>> = _uiState

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>>
        get() = _products

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean>
        get() = _isLoadMoreButtonVisible

    init {
        loadProducts()
    }

    private fun getProducts(): List<Product> {
        val fromIndex = offset
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(fromIndex, offset)
    }

    fun loadProducts() {
        try {
            productsData = repository.getAllProducts()
            if (productsData.isEmpty()) {
                _uiState.postValue(UIState.Empty)
            } else {
                _uiState.postValue(UIState.Success(productsData))
            }
            _products.postValue(_products.value.orEmpty() + getProducts())
        } catch (e: Exception) {
            _uiState.postValue(UIState.Error(e))
        }
    }

    fun updateLoadMoreButtonVisibility(isVisible: Boolean) {
        if (offset == productsData.size) {
            _isLoadMoreButtonVisible.postValue(false)
        } else {
            _isLoadMoreButtonVisible.postValue(isVisible)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
