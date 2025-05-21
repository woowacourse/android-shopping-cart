package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ResultState

class ProductDetailViewModel(
    private val cartDataSource: CartDataSource,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product
    private val _insertProductResult: MutableLiveData<ResultState<Unit>> = MutableLiveData()
    val insertProductResult: LiveData<ResultState<Unit>> = _insertProductResult

    fun fetchData(product: Product) {
        _product.value = product
    }

    fun addToCart(product: Product) {
        cartDataSource.insertProduct(product) { result ->
            result
                .onSuccess { _insertProductResult.postValue(ResultState.Success(it)) }
                .onFailure { _insertProductResult.postValue(ResultState.Failure()) }
        }
    }
}
