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
    val productsInShop: LiveData<List<Product>> get() = _productsInShop
    private val pageSize = 20
    private var currentPage = 0
    private val loadedItems = mutableListOf<Product>()
    private val _isAllProductsFetched = MutableLiveData(false)
    val isAllProductsFetched: LiveData<Boolean> get() = _isAllProductsFetched

    init {
        loadNextPage()
    }

    fun loadNextPage() {
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
