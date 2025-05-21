package woowacourse.shopping.viewmodel.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.products.ProductListViewType

class ProductsViewModel(
    private val products: DummyProducts,
) : ViewModel() {
    private val _productsInShop = MutableLiveData<List<ProductListViewType>>()
    val productsInShop: LiveData<List<ProductListViewType>> get() = _productsInShop
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
        val nextEnd = minOf(nextStart + pageSize, products.dummyProducts.size)

        if (nextStart < products.dummyProducts.size) {
            val nextItems = products.dummyProducts.subList(nextStart, nextEnd)
            loadedItems.addAll(nextItems)

            if (nextEnd == products.dummyProducts.size) {
                _productsInShop.value =
                    loadedItems.map { ProductListViewType.ProductType(it) }
                currentPage++
            } else {
                _productsInShop.value =
                    loadedItems.map { ProductListViewType.ProductType(it) } + ProductListViewType.LoadMoreType
                currentPage++
            }
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
                        DummyProducts,
                    ) as T
                }
            }
    }
}
