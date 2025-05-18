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
    val products: LiveData<List<Product>> = _products
    private val _totalPages: MutableLiveData<Int> = MutableLiveData(0)
    val totalPages: LiveData<Int> = _totalPages
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    val toastMessage = SingleLiveData<Int>()

    fun loadItems() {
        val page = _currentPage.value ?: 0
        productRepository.getPagedCartProducts(PAGE_SIZE, page) { pagedProducts ->
            _products.postValue(pagedProducts)
        }

        productRepository.getCartProductCount { count ->
            updateTotalPage(count)
        }
    }

    fun changePage(next: Boolean) {
        val currentPage = _currentPage.value ?: 0
        val totalPages = _totalPages.value ?: 0

        if (!next && currentPage == 0) {
            _toastMessage.value = R.string.cart_first_page_toast
            return
        }

        if (next && currentPage >= totalPages - 1) {
            _toastMessage.value = R.string.cart_last_page_toast
            return
        }

        _currentPage.value = if (next) currentPage + 1 else currentPage - 1
        loadItems()
    }

    fun deleteProduct(product: Product) {
        val currentPage = _currentPage.value ?: 0

        productRepository.deleteProduct(product.productId) {
            productRepository.getPagedCartProducts(PAGE_SIZE, currentPage) { pagedProducts ->

                if (pagedProducts.isEmpty()) {
                    productRepository.getCartProductCount { count ->
                        updateTotalPage(count)
                        Handler(Looper.getMainLooper()).post {
                            changePage(false)
                        }
                    }
                } else {
                    _products.postValue(pagedProducts)
                    productRepository.getCartProductCount { count ->
                        updateTotalPage(count)
                    }
                }
            }
        }
    }

    override fun onClickPrevious() {
        changePage(next = false)
    }

    override fun onClickNext() {
        changePage(next = true)
    }

    private fun updateTotalPage(totalSize: Int) {
        _totalPages.postValue((totalSize + PAGE_SIZE - 1) / PAGE_SIZE)
    }

    companion object {
        private const val PAGE_SIZE = 5

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer { CartViewModel(ShoppingApplication.provideProductRepository()) }
            }
    }
}
