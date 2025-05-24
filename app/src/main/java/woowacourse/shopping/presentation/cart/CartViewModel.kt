package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<ResultState<List<CartItem>>> = MutableLiveData()
    val products: LiveData<ResultState<List<CartItem>>> = _products
    private val _totalPages: MutableLiveData<Int> = MutableLiveData(0)
    val totalPages: LiveData<Int> = _totalPages
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    init {
        loadItems(FIRST_PAGE)
    }

    fun loadItems(currentPage: Int) {
        productRepository.fetchPagedCartItems(PAGE_SIZE, currentPage) { result ->
            result
                .onSuccess { pagedProducts -> _products.postValue(ResultState.Success(pagedProducts)) }
                .onFailure { _products.postValue(ResultState.Failure()) }
        }
        cartRepository.getCartItemCount { result ->
            result
                .onSuccess { count -> updateTotalPage(count) }
                .onFailure { _products.postValue(ResultState.Failure()) }
        }
    }

    fun changeNextPage() {
        val currentPage = _currentPage.value ?: 0
        val totalPages = _totalPages.value ?: 0

        if (currentPage >= totalPages - 1) {
            _toastMessage.value = R.string.cart_toast_last_page
            return
        }

        _currentPage.value = currentPage + 1
    }

    fun changePreviousPage() {
        val currentPage = _currentPage.value ?: 0

        if (currentPage == 0) {
            _toastMessage.value = R.string.cart_toast_first_page
            return
        }

        _currentPage.value = currentPage - 1
    }

    fun deleteProduct(cartItem: CartItem) {
        val currentPage = _currentPage.value ?: 0

        cartRepository.deleteProduct(cartItem.product.productId) { result ->
            result
                .onSuccess {
                    reloadProductsByPage(currentPage)
                }.onFailure {
                    _toastMessage.value = R.string.cart_toast_delete_fail
                }
        }
    }

    private fun reloadProductsByPage(currentPage: Int) {
        productRepository.fetchPagedCartItems(PAGE_SIZE, currentPage) { result ->
            result
                .onSuccess { pagedProducts ->
                    if (pagedProducts.isEmpty()) {
                        handleEmptyPage()
                    } else {
                        _products.postValue(ResultState.Success(pagedProducts))
                        updateTotalPageAsync()
                    }
                }.onFailure { _products.postValue(ResultState.Failure()) }
        }
    }

    private fun handleEmptyPage() {
        val currentPage = _currentPage.value ?: 0
        if (currentPage > 0) {
            _currentPage.postValue(currentPage - 1)
            reloadProductsByPage(currentPage - 1)
        } else {
            _products.postValue(ResultState.Success(emptyList()))
        }
    }

    fun increaseQuantity(productId: Long) {
        cartRepository.increaseQuantity(productId, 1) { result ->
            result
                .onSuccess {
                    updateQuantity(productId, 1)
                }.onFailure {
                    _toastMessage.value = R.string.cart_toast_increase_fail
                }
        }
    }

    fun decreaseQuantity(productId: Long) {
        val currentItems = (_products.value as? ResultState.Success)?.data ?: return
        val item = currentItems.find { it.product.productId == productId } ?: return

        if (item.quantity == 1) {
            _toastMessage.value = R.string.cart_toast_invalid_quantity
            return
        }

        cartRepository.decreaseQuantity(productId) { result ->
            result
                .onSuccess {
                    updateQuantity(productId, -1)
                }.onFailure {
                    _toastMessage.value = R.string.cart_toast_decrease_fail
                }
        }
    }

    private fun updateQuantity(
        productId: Long,
        amount: Int,
    ) {
        val currentItems = (_products.value as? ResultState.Success)?.data ?: return
        val updatedItem =
            currentItems.map {
                if (it.product.productId == productId) it.copy(quantity = it.quantity + amount) else it
            }
        _products.postValue(ResultState.Success(updatedItem))
    }

    private fun updateTotalPageAsync(onComplete: (() -> Unit)? = null) {
        cartRepository.getCartItemCount { result ->
            result
                .onSuccess { count ->
                    updateTotalPage(count)
                    onComplete?.invoke()
                }.onFailure { _products.postValue(ResultState.Failure()) }
        }
    }

    private fun updateTotalPage(totalSize: Int?) {
        if (totalSize == null) return
        _totalPages.postValue((totalSize + PAGE_SIZE - 1) / PAGE_SIZE)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 5
    }
}
