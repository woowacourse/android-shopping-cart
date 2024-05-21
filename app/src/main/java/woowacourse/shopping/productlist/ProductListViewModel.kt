package woowacourse.shopping.productlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ProductRepository

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<ProductUiModel>> = MutableLiveData(emptyList())
    val products: LiveData<List<ProductUiModel>> get() = _products

    private val _moreProducts: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val moreProducts: LiveData<List<ProductUiModel>> get() = _moreProducts

    val totalSize: Int = repository.productsTotalSize()

    fun loadProducts() {
        runCatching {
            repository.products(_products.value?.size ?: 0, PRODUCTS_OFFSET_SIZE)
        }.onSuccess { newProducts ->
            val newProductsUiModel = newProducts.map { it.toProductUiModel() }
            _products.value = newProductsUiModel + (_products.value ?: emptyList())
            _moreProducts.value = newProductsUiModel
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    companion object {
        private const val PRODUCTS_OFFSET_SIZE = 20
    }
}
