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
    val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products
    private var _totalSize: MutableLiveData<Int> = MutableLiveData()
    val totalSize: LiveData<Int> get() = _totalSize
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    private val pageSize = 5

    fun fetchData() {
        thread {
            _totalSize.postValue(productRepository.getCartProducts().size)
            val firstPage =
                productRepository.getPagedCartProducts(pageSize, (_currentPage.value) ?: 0)
            _products.postValue(firstPage)
        }
    }

    fun changePage(next: Boolean) {
        val current = _currentPage.value ?: 0
        val newPage = if (next) current + 1 else maxOf(0, current - 1)
        _currentPage.value = newPage

        thread {
            val newProducts = productRepository.getPagedCartProducts(pageSize, newPage)
            _products.postValue(newProducts)
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
