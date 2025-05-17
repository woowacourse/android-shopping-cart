package woowacourse.shopping.presentation.cart

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.SingleLiveData

class CartViewModel(
    private val productRepository: ProductRepository,
) : ViewModel(),
    CartClickHandler {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products
    private var _totalSize: MutableLiveData<Int> = MutableLiveData()
    val totalSize: LiveData<Int> get() = _totalSize
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    val toastMessage = SingleLiveData<Int>()

    private var productCount = 0

    fun loadItems() {
        val page = _currentPage.value ?: 0
        productRepository.getPagedCartProducts(PAGE_SIZE, page) { pagedProducts ->
            _products.postValue(pagedProducts)
        }

        productRepository.getCartProducts { allProducts ->
            _totalSize.postValue(allProducts.size)
        }
    }

    fun changePage(next: Boolean) {
        val currentPage = _currentPage.value ?: 0
        val totalPages = calculateTotalPages()

        if (!next && currentPage == 0) {
            toastMessage.value = R.string.cart_first_page_toast
            return
        }

        if (next && currentPage >= totalPages - 1) {
            toastMessage.value = R.string.cart_last_page_toast
            return
        }

        _currentPage.value = if (next) currentPage + 1 else currentPage - 1
        loadItems()
    }

    fun deleteProduct(product: Product) {
        val currentPage = _currentPage.value ?: 0

        productRepository.deleteProduct(product.productId) {
            productRepository.getPagedCartProducts(PAGE_SIZE, currentPage) { pagedProducts ->
                productCount = pagedProducts.size

                if (productCount == 0) {
                    productRepository.getCartProducts { allProducts ->
                        _totalSize.postValue(allProducts.size)
                    }

                    Handler(Looper.getMainLooper()).post {
                        changePage(false)
                    }
                } else {
                    _products.postValue(pagedProducts)

                    productRepository.getCartProducts { allProducts ->
                        _totalSize.postValue(allProducts.size)
                    }
                }
            }
        }
    }

    fun calculateTotalPages(): Int {
        val total = _totalSize.value ?: 0
        return (total + PAGE_SIZE - 1) / PAGE_SIZE
    }

    override fun onClickPrevious() {
        changePage(next = false)
    }

    override fun onClickNext() {
        changePage(next = true)
    }

    companion object {
        private const val PAGE_SIZE = 5

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer { CartViewModel(ShoppingApplication.provideProductRepository()) }
            }
    }
}
