package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.main.MainActivity.Companion.PAGE_SIZE

class MainViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> = _products

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    init {
        loadProducts(0, PAGE_SIZE)
    }

    fun loadProducts(
        itemCount: Int,
        pageSize: Int,
    ) {
        val newPage = itemCount / pageSize
        val loadedProducts = productRepository.loadSinglePage(newPage, pageSize)

        val currentList = _products.value ?: emptyList()

        _products.value = currentList + loadedProducts
        _loadState.value = LoadState.of(loadedProducts.isNotEmpty())
    }
}
