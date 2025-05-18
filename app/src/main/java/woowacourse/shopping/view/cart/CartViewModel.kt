package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.cartRepository.CartRepository
import woowacourse.shopping.domain.Product

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _totalProductsCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalProductsCount: LiveData<Int> get() = _totalProductsCount
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    fun fetchData() {
        cartRepository.getAllProducts { products ->
            _totalProductsCount.postValue(products.size)
        }

        cartRepository.getProducts(limit = LIMIT_COUNT) { products ->
            _products.postValue(products)
        }
    }

    fun deleteProduct(product: Product) {
        cartRepository.deleteProduct(product.id)
    }

    companion object {
        private const val LIMIT_COUNT = 5

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val cartRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).cartRepository
                    CartViewModel(
                        cartRepository = cartRepository,
                    )
                }
            }
    }
}
