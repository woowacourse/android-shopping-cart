package woowacourse.shopping.presentation.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product
    private val _resultState = SingleLiveData<ResultState>()
    val resultState: LiveData<ResultState> = _resultState

    fun fetchData(product: Product) {
        _product.value = product
    }

    fun addToCart(product: Product) {
        productRepository.insertProduct(product) { result ->
            result
                .onSuccess { _resultState.postValue(ResultState.INSERT_SUCCESS) }
                .onFailure { _resultState.postValue(ResultState.INSERT_FAILURE) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer { ProductDetailViewModel(ShoppingApplication.provideProductRepository()) }
            }
    }
}
