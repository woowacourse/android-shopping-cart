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

    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    init {
        loadProducts(0, 20)
    }

    fun loadProducts(
        page: Int,
        pageSize: Int,
    ) {
        val loadedProducts = storage.getProducts(page, pageSize)
        val currentList = _products.value ?: emptyList()
        _products.value = currentList + loadedProducts
        _isLastPage.value = products.value?.count() == storage.productCount()
    }
}
