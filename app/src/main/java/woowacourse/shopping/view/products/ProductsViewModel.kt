package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.Products

class ProductsViewModel(
    private val products: Products,
) : ViewModel() {
    private val _productsInShop = MutableLiveData<List<Product>>()
    val productsInShop: LiveData<List<Product>> = _productsInShop

    private val _isAllProductsFetched = MutableLiveData(false)
    val isAllProductsFetched: LiveData<Boolean> = _isAllProductsFetched

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean> = _isLoadMoreButtonVisible

    private var currentPage = 0
    private val loadedItems = mutableListOf<Product>()

    init {
        loadPage()
    }

    fun loadPage() {
        val pageSize = 20
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, products.value.size)

        if (nextStart < products.value.size) {
            val nextItems = products.value.subList(nextStart, nextEnd)
            loadedItems.addAll(nextItems)
            _productsInShop.value = loadedItems.toList()
            currentPage++
            if (nextEnd == products.value.size) _isAllProductsFetched.value = true
        }
    }

    fun updateButtonVisibility(isVisible: Boolean) {
        _isLoadMoreButtonVisible.value = isVisible
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return ProductsViewModel(
                        Products(),
                    ) as T
                }
            }
    }
}
