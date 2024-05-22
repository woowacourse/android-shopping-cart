package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.uimodel.ProductUiModel
import woowacourse.shopping.uimodel.toProductUiModel

class ProductListViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _productUiModels: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val productUiModels: LiveData<List<ProductUiModel>> get() = _productUiModels

    private val _currentSize: MutableLiveData<Int> = MutableLiveData()
    val currentSize: LiveData<Int> get() = _currentSize

    val totalSize: Int = repository.productsTotalSize()

    init {
        loadProducts(PRODUCTS_START_POSITION)
    }

    fun loadProducts(startPosition: Int) {
        runCatching {
            repository.products(startPosition, PRODUCTS_OFFSET_SIZE)
        }.onSuccess { loadedProducts ->
            val currentItems = products.value ?: emptyList()
            val currentUiItems = productUiModels.value ?: emptyList()
            _products.value = currentItems + loadedProducts
            _productUiModels.value =
                currentUiItems + loadedProducts.map { it.toProductUiModel() }
            _currentSize.value = products.value?.size
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    companion object {
        private const val PRODUCTS_START_POSITION = 0
        private const val PRODUCTS_OFFSET_SIZE = 20
    }
}
