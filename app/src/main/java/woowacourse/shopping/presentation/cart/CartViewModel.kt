package woowacourse.shopping.presentation.cart

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.productdetail.ProductDetailViewModel

class CartViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products
    private var _totalSize: MutableLiveData<Int> = MutableLiveData()
    val totalSize: LiveData<Int> get() = _totalSize
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    private var productCount = 0

    fun fetchData() {
        productRepository.getCartProducts { allProducts ->
            _totalSize.postValue(allProducts.size)
        }

        val currentPage = _currentPage.value ?: 0
        productRepository.getPagedCartProducts(PAGE_SIZE, currentPage) { pagedProducts ->
            _products.postValue(pagedProducts)
        }
    }

    fun changePage(next: Boolean) {
        val current = _currentPage.value ?: 0
        val newPage = if (next) current + 1 else maxOf(0, current - 1)
        _currentPage.value = newPage

        productRepository.getPagedCartProducts(PAGE_SIZE, newPage) { newProducts ->
            _products.postValue(newProducts)
        }
    }

    fun deleteProduct(product: Product) {
        val currentPage = _currentPage.value ?: 0

        productRepository.deleteProduct(product.productId) {
            productRepository.getPagedCartProducts(PAGE_SIZE, currentPage) { pagedProducts ->
                productCount = pagedProducts.size

                if (productCount == 0) {
                    _currentPage.postValue(currentPage)
                    productRepository.getCartProducts { allProducts ->
                        _totalSize.postValue(allProducts.size)
                    }

                    calculateTotalPages()
                    Handler(Looper.getMainLooper()).post {
                        changePage(false)
                    }
                } else {
                    productRepository.getPagedCartProducts(
                        PAGE_SIZE,
                        currentPage,
                    ) { _products.postValue(it) }
                }
            }
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
                initializer { CartViewModel(ShoppingApplication.provideProductRepository()) }
            }
    }
}
