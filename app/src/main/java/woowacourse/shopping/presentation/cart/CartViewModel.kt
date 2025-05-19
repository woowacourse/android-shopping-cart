package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData

class CartViewModel(
    private val productRepository: ProductRepository,
) : ViewModel(),
    CartClickHandler {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> = _products
    private val _deleteProduct: MutableLiveData<Long> = MutableLiveData()
    val deleteProduct: LiveData<Long> = _deleteProduct
    private val _totalPages: MutableLiveData<Int> = MutableLiveData(0)
    val totalPages: LiveData<Int> = _totalPages
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage
    private val _resultState: MutableLiveData<ResultState> = MutableLiveData<ResultState>()
    val resultState: LiveData<ResultState> = _resultState
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    init {
        loadItems()
    }

    private fun loadItems() {
        val page = _currentPage.value ?: 0
        productRepository.getPagedCartProducts(PAGE_SIZE, page) { result ->
            result
                .onSuccess { pagedProducts -> _products.postValue(pagedProducts) }
                .onFailure { _resultState.postValue(ResultState.LOAD_FAILURE) }
        }

        productRepository.getCartProductCount { result ->
            result
                .onSuccess { count -> updateTotalPage(count) }
                .onFailure { _resultState.postValue(ResultState.LOAD_FAILURE) }
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

        productRepository.deleteProduct(product.productId) { result ->
            result
                .onSuccess { productId ->
                    reloadProductsByPage(currentPage)
                    _deleteProduct.postValue(productId)
                }.onFailure {
                    _resultState.postValue(ResultState.DELETE_FAILURE)
                }
        }
    }

    private fun reloadProductsByPage(currentPage: Int) {
        productRepository.getPagedCartProducts(PAGE_SIZE, currentPage) { result ->
            result
                .onSuccess { pagedProducts ->
                    if (pagedProducts.isEmpty()) {
                        handleEmptyPage()
                    } else {
                        _products.postValue(pagedProducts)
                        updateTotalPageAsync()
                    }
                }.onFailure {
                    _resultState.postValue(ResultState.LOAD_FAILURE)
                }
        }
    }

    private fun handleEmptyPage() {
        val currentPage = _currentPage.value ?: 0
        if (currentPage > 0) {
            _currentPage.postValue(currentPage - 1)
            reloadProductsByPage(currentPage - 1)
        } else {
            _products.postValue(emptyList())
        }
    }

    override fun onClickPrevious() {
        changePage(next = false)
    }

    override fun onClickNext() {
        changePage(next = true)
    }

    private fun updateTotalPageAsync(onComplete: (() -> Unit)? = null) {
        productRepository.getCartProductCount { result ->
            result
                .onSuccess { count ->
                    updateTotalPage(count)
                    onComplete?.invoke()
                }.onFailure { _resultState.postValue(ResultState.LOAD_FAILURE) }
        }
    }

    private fun updateTotalPage(totalSize: Int) {
        _totalPages.postValue((totalSize + PAGE_SIZE - 1) / PAGE_SIZE)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
