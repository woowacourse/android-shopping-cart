package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toUiModel

class CatalogViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<Pair<List<ProductUiModel>, Boolean>>()
    val products: LiveData<Pair<List<ProductUiModel>, Boolean>> = _products

    private val loadSize: Int = 20
    private var lastId: Long = DEFAULT_ID

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        productRepository.loadProducts(lastId, loadSize) { newProducts, hasMore ->
            val newProductsUiModels = newProducts.map { it.toUiModel() }

            lastId = newProductsUiModels.lastOrNull()?.id ?: DEFAULT_ID

            val updatedProducts = (_products.value?.first ?: emptyList()).plus(newProductsUiModels).distinct()

            _products.postValue(
                updatedProducts to hasMore,
            )
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
