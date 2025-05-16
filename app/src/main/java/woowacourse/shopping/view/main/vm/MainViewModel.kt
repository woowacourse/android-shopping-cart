package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.domain.Product

class MainViewModel(
    private val storage: ProductStorage,
) : ViewModel() {
    private val _products = MutableLiveData(emptyList<Product>())
    val products: LiveData<List<Product>> = _products

    private val _loadState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> = _loadState

    fun loadProducts(pageSize: Int) {
        val newPage = (products.value?.size ?: 0) / pageSize
        val loadedProducts = storage.getProducts(newPage, pageSize)

        val currentList = _products.value ?: emptyList()
        val loadable = storage.hasMoreProduct(newPage + 1, pageSize)

        _products.value = currentList + loadedProducts
        _loadState.value = LoadState.of(loadable)
    }
}
