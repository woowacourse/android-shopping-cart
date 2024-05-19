package woowacourse.shopping.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.repository.DummyProductStore

class ProductListViewModel : ViewModel() {
    private val dummyProductStore by lazy { DummyProductStore() }
    private var currentIndex = 1

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(loadProducts())

    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    fun loadMoreProducts() {
        val newProducts = loadProducts()
        val currentProducts = _loadedProducts.value.orEmpty()
        _loadedProducts.value = currentProducts + newProducts
    }

    private fun loadProducts(): List<Product> {
        val result = dummyProductStore.loadDataAsNeeded(currentIndex)
        currentIndex += COUNT_EACH_LOADING
        return result
    }

    companion object {
        private const val COUNT_EACH_LOADING = 20
    }
}
