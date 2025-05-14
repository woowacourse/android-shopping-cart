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

    private val _loadable = MutableLiveData(false)
    val loadable: LiveData<Boolean> = _loadable

    fun loadProducts(
        page: Int,
        pageSize: Int,
    ) {
        val newPage = page / pageSize
        val loadedProducts = storage.getProducts(newPage, pageSize)

        val currentList = _products.value ?: emptyList()
        _products.value = currentList + loadedProducts

        _loadable.value = storage.notHasMoreProduct(newPage + 1, pageSize)
    }
}
