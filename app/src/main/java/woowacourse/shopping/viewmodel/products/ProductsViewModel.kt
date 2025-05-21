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
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ProductRepositoryImpl
import woowacourse.shopping.view.products.ProductListViewType

class ProductsViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _productsInShop = MutableLiveData<List<ProductListViewType>>()
    val productsInShop: LiveData<List<ProductListViewType>> get() = _productsInShop
    private val pageSize = 20
    private var currentPage = 0
    private val loadedItems = mutableListOf<Product>()

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, productRepository.getSize())

        if (nextStart < productRepository.getSize()) {
            val nextItems = productRepository.getSinglePage(nextStart, nextEnd)
            loadedItems.addAll(nextItems)

            if (nextEnd == productRepository.getSize()) {
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
                        ProductRepositoryImpl(storage = DummyProducts),
                    ) as T
                }
            }
    }
}
