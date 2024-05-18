package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingViewModel(private val repository: ShoppingItemsRepository) : ViewModel() {
    private val productsData: List<Product> by lazy { repository.getAllProducts() }

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>>
        get() = _products

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean>
        get() = _isLoadMoreButtonVisible

    private var offset = 0

    init {
        loadProducts()
    }

    private fun getProducts(): List<Product> {
        val fromIndex = offset
        offset = Integer.min(offset + PAGE_SIZE, productsData.size)
        return productsData.subList(fromIndex, offset)
    }

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty()
        val newProducts = getProducts()
        _products.postValue(currentProducts + newProducts)
    }

    fun updateLoadMoreButtonVisibility(isVisible: Boolean) {
        _isLoadMoreButtonVisible.postValue(isVisible)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
