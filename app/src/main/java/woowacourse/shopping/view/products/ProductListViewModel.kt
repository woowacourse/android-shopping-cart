package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _productListState: MutableLiveData<ProductListState> =
        MutableLiveData()
    val productListState: LiveData<ProductListState> get() = _productListState

    private val _errorState: MutableLiveData<ProductListState.ErrorState> =
        MutableLiveData()
    val errorState: LiveData<ProductListState.ErrorState> get() = _errorState

    fun loadPagingProduct() {
        try {
            val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
            val pagingData = repository.loadPagingProducts(itemSize)
            _products.value = _products.value?.plus(pagingData)
            _productListState.value = ProductListState.LoadProductList.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorState.value =
                        ProductListState.LoadProductList.Fail

                else -> _errorState.value = ProductListState.ErrorState.NotKnownError
            }
        }
    }
}
