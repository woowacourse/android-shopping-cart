package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModel(val repository: ShoppingItemsRepository) : ViewModel() {
    private val productsData: List<Product> by lazy { repository.getAllProducts() }

    private val _products = MutableLiveData(getProducts())
    val products: LiveData<List<Product>>
        get() = _products

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean>
        get() = _isLoadMoreButtonVisible

    private var offset = 0

    private fun getProducts(): List<Product> {
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(0, offset)
    }

    fun loadProducts() {
        _products.postValue(getProducts())
    }

    fun updateLoadMoreButtonVisibility(isVisible: Boolean) {
        _isLoadMoreButtonVisible.postValue(isVisible)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
