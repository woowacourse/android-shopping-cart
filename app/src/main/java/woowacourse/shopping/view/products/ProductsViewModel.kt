package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.products.ProductRepository
import woowacourse.shopping.data.products.ProductRepositoryImpl
import woowacourse.shopping.model.product.Product

class ProductsViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _productsInShop = MutableLiveData<List<Product>>()
    val productsInShop: LiveData<List<Product>> = _productsInShop

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean> = _isLoadMoreButtonVisible

    private var isAllProductsFetched = false
    private var currentPage = INITIAL_PAGE
    private val loadedItems = mutableListOf<Product>()

    init {
        loadPage()
    }

    fun loadPage() {
        val pageSize = PAGE_SIZE
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, productRepository.dummyProducts.size)

        if (nextStart < productRepository.dummyProducts.size) {
            val nextItems = productRepository.fetchProducts(nextStart, nextEnd)
            loadedItems.addAll(nextItems)
            _productsInShop.value = loadedItems.toList()
            currentPage++
            if (nextEnd == productRepository.dummyProducts.size) isAllProductsFetched = true
        }
    }

    fun updateButtonVisibility(canLoadMore: Boolean) {
        _isLoadMoreButtonVisible.value = canLoadMore && !isAllProductsFetched
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val PAGE_SIZE = 20

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
                        ProductRepositoryImpl(),
                    ) as T
                }
            }
    }
}
