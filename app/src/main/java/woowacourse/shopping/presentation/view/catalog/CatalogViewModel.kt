package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem

class CatalogViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _items = MutableLiveData<List<CatalogItem>>()
    val items: LiveData<List<CatalogItem>> = _items

    private val loadSize: Int = 20
    private var lastId: Long = DEFAULT_ID

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        productRepository.loadProducts(lastId, loadSize) { fetchedProducts, hasMore ->
            val fetchedUiModels = fetchedProducts.map { it.toUiModel() }
            lastId = fetchedUiModels.lastOrNull()?.id ?: DEFAULT_ID

            val currentUiModels =
                _items.value
                    .orEmpty()
                    .filterIsInstance<CatalogItem.ProductItem>()
                    .map { it.product }

            val combinedUiModels =
                (currentUiModels + fetchedUiModels)
                    .distinctBy { it.id }

            val updatedItems =
                combinedUiModels
                    .map { CatalogItem.ProductItem(it) }
                    .toMutableList<CatalogItem>()

            if (hasMore && updatedItems.none { it is CatalogItem.LoadMoreItem }) {
                updatedItems.add(CatalogItem.LoadMoreItem)
            }

            _items.postValue(updatedItems)
        }
    }

    companion object {
        private const val DEFAULT_ID = 0L

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val repository = RepositoryProvider.productRepository
                    return CatalogViewModel(repository) as T
                }
            }
    }
}
