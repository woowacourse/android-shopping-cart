package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.Event
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(private val repository: ShoppingItemsRepository) :
    ViewModel(),
    ShoppingClickListener {
    private val _shoppingUiState = MutableLiveData<UIState<List<Product>>>(UIState.Empty)
    val shoppingUiState: LiveData<UIState<List<Product>>>
        get() = _shoppingUiState

    private val _canLoadMore = MutableLiveData(false)
    val canLoadMore: LiveData<Boolean>
        get() = _canLoadMore

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val loadedProducts: MutableList<Product> = mutableListOf()

    init {
        loadProducts()
    }

    fun loadProducts() {
        try {
            val products = repository.findProductsByPage()
            if (products.isEmpty()) {
                _shoppingUiState.value = UIState.Empty
            } else {
                _canLoadMore.value = repository.canLoadMore()
                _shoppingUiState.value = UIState.Success(loadedProducts + products)
                loadedProducts += products
            }
        } catch (e: Exception) {
            _shoppingUiState.value = UIState.Error(e)
        }
    }

    override fun onLoadMoreButtonClick() {
        loadProducts()
    }

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun onProductClick(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }
}
