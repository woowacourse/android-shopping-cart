package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(private val repository: ShoppingItemsRepository) : ViewModel() {
    private val _productItemsState = MutableLiveData<UIState<List<Product>>>()
    val productItemsState: LiveData<UIState<List<Product>>> = _productItemsState

    private val _navigateToProductDetail = MutableLiveData<Long>()
    val navigateToProductDetail: LiveData<Long> = _navigateToProductDetail

    private val _productListVisibility = MutableLiveData<Boolean>()
    val productListVisibility: LiveData<Boolean> = _productListVisibility

    private val _loadMoreVisibility = MutableLiveData<Boolean>()
    val loadMoreVisibility: LiveData<Boolean> = _loadMoreVisibility

    private var currentPage = 0
    private val pageSize = 20

    init {
        loadProducts()
    }

    fun loadProducts() {
        try {
            val productList = repository.findProductsByPage(currentPage, pageSize)
            if (productList.isEmpty()) {
                _productItemsState.postValue(UIState.Empty)
                _productListVisibility.postValue(false)
            } else {
                _productItemsState.postValue(UIState.Success(productList))
                if (productList.size < pageSize) {
                    updateVisibility(false)
                } else {
                    updateVisibility(true)
                }
            }
        } catch (e: Exception) {
            _productItemsState.postValue(UIState.Error(e))
        }
    }

    fun onProductClick(productId: Long) {
        _navigateToProductDetail.postValue(productId)
    }

    fun updateVisibility(visibility: Boolean) {
        _loadMoreVisibility.postValue(visibility)
    }

    fun onLoadMoreButtonClick() {
        currentPage++
        loadProducts()
        updateVisibility(false)
    }
}
