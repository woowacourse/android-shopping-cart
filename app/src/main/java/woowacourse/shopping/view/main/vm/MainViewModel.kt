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

    init {
        loadProducts(0, 20)
    }

    fun loadProducts(
        page: Int,
        pageSize: Int,
    ) {
        val loadedProducts = storage.getProducts(page, pageSize)
        _products.value = (products.value ?: emptyList()) + loadedProducts
    }
}
