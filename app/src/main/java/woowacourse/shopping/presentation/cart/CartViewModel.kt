package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import kotlin.concurrent.thread

class CartViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    fun fetchData() {
        thread {
            _products.postValue(productRepository.getCartProducts())
        }
    }

    fun deleteProduct(product: Product) {
        thread {
            productRepository.deleteProduct(product.productId)
            _products.postValue(productRepository.getCartProducts())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val productRepository =
                        ShoppingApplication.instance.provideProductRepository()
                    CartViewModel(
                        productRepository = productRepository,
                    )
                }
            }
    }
}
