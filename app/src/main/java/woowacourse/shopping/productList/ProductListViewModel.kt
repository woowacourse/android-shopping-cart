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
        MutableLiveData(
            dummyProductStore.load20Data(currentIndex).toMutableList()
                .also { currentIndex += COUNT_EACH_LOADING },
        )

    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    fun loadProducts() {
        val result = dummyProductStore.load20Data(currentIndex)
        currentIndex += COUNT_EACH_LOADING
        _loadedProducts.value =
            _loadedProducts.value?.toMutableList()?.apply {
                addAll(result)
            }
    }

    companion object {
        private const val COUNT_EACH_LOADING = 20
    }
}
