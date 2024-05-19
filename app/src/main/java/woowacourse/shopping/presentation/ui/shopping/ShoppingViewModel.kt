package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(private val repository: ShoppingItemsRepository) : ViewModel() {
    private val _currentPage = MutableLiveData(0)
    private val currentPage: LiveData<Int> = _currentPage

    val productItemsState: LiveData<UIState<List<Product>>> =
        currentPage.switchMap { page ->
            MutableLiveData<UIState<List<Product>>>().apply {
                value =
                    try {
                        val productList = repository.findProductsByPage(page, PAGE_SIZE)
                        if (productList.isEmpty()) {
                            UIState.Empty
                        } else {
                            UIState.Success(productList)
                        }
                    } catch (e: Exception) {
                        UIState.Error(e)
                    }
            }
        }

    private val _navigateToProductDetail = MutableLiveData<Long>()
    val navigateToProductDetail: LiveData<Long> = _navigateToProductDetail

    private val _productListVisibility = MutableLiveData<Boolean>()
    val productListVisibility: LiveData<Boolean> = _productListVisibility

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _currentPage.value = 0
    }

    fun onProductClick(productId: Long) {
        _navigateToProductDetail.postValue(productId)
    }

    fun onLoadMoreButtonClick() {
        val nextPage = (_currentPage.value ?: 0) + 1
        _currentPage.value = nextPage
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
