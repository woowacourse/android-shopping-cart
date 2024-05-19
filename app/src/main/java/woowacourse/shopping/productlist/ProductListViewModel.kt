package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository

class ProductListViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val products: LiveData<List<ProductUiModel>> get() = _products

    private val _currentSize: MutableLiveData<Int> = MutableLiveData()
    val currentSize: LiveData<Int> get() = _currentSize

    val totalSize: Int = repository.productsTotalSize()

    fun loadProducts(startPosition: Int) {
        runCatching {
            repository.products(startPosition, PRODUCTS_OFFSET_SIZE)
        }.onSuccess { newProducts ->
            val currentItems = _products.value ?: emptyList()
            _products.value = currentItems + newProducts.map { it.toProductUiModel() }
            _currentSize.value = products.value?.size
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    companion object {
        private const val PRODUCTS_OFFSET_SIZE = 20
    }
}
