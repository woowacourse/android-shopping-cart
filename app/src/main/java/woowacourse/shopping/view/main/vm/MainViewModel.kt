package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> = _products

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    init {
        loadProducts()
    }

    fun loadProducts() {
        val itemCount = (_products.value?.size ?: 0) + 1

        val newPage = itemCount / PAGE_SIZE
        val currentList = _products.value ?: emptyList()

        val result = productRepository.loadSinglePage(newPage, PAGE_SIZE)

        _products.value = currentList + result.products
        _loadState.value = LoadState.of(result.hasNextPage)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
