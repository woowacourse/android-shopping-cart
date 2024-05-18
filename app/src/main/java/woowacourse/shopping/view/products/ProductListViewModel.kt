package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.PRODUCT_LOAD_PAGING_SIZE
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _productListState: MutableLiveData<ProductListState> =
        MutableLiveData(ProductListState.Init)
    val productListState: LiveData<ProductListState> get() = _productListState

    fun loadPagingProduct() {
        runCatching {
            val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
            repository.loadPagingProducts(itemSize)
        }
            .onSuccess {
                _products.value = _products.value?.plus(it)
                _productListState.value = ProductListState.LoadProductList.Success
                resetState()
            }
            .onFailure {
                _productListState.value = ProductListState.LoadProductList.Fail
                resetState()
            }
    }

    private fun resetState() {
        _productListState.value = ProductListState.Init
    }
}
