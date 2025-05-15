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
    private var _totalSize: MutableLiveData<Int> = MutableLiveData()
    val totalSize: LiveData<Int> get() = _totalSize
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    fun fetchData() {
        thread {
            val totalSize = productRepository.getCartProducts().size
            _totalSize.postValue(totalSize)
            val firstPage =
                productRepository.getPagedCartProducts(PAGE_SIZE, (_currentPage.value) ?: 0)
            _products.postValue(firstPage)
        }
    }

    fun changePage(next: Boolean) {
        val current = _currentPage.value ?: 0
        val newPage = if (next) current + 1 else maxOf(0, current - 1)
        _currentPage.value = newPage

        thread {
            val newProducts = productRepository.getPagedCartProducts(PAGE_SIZE, newPage)
            _products.postValue(newProducts)
        }
    }

    fun deleteProduct(product: Product) {
        thread {
            productRepository.deleteProduct(product.productId)
            _products.postValue(
                productRepository.getPagedCartProducts(PAGE_SIZE, (_currentPage.value) ?: 0),
            )
        }
    }

    fun calculateTotalPages(): Int {
        val total = _totalSize.value ?: 0
        return (total + PAGE_SIZE - 1) / PAGE_SIZE
    }

    companion object {
        private const val PAGE_SIZE = 5

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
