package woowacourse.shopping.presentation.cart

import android.util.Log
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
) : ViewModel(),
    CartPageClickListener {
    private val _products: MutableLiveData<ResultState<List<CartItem>>> = MutableLiveData()
    val products: LiveData<ResultState<List<CartItem>>> = _products
    private val _totalPages: MutableLiveData<Int> = MutableLiveData(0)
    val totalPages: LiveData<Int> = _totalPages
    private val _currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val currentPage: LiveData<Int> = _currentPage
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    init {
        loadItems()
    }

    private fun loadItems() {
        val page = _currentPage.value ?: 0

        productRepository.getPagedCartItems(PAGE_SIZE, page) { result ->
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

    fun deleteProduct(cartItem: CartItem) {
        val currentPage = _currentPage.value ?: 0

        cartRepository.deleteProduct(cartItem.product.productId) { result ->
            result
                .onSuccess {
                    reloadProductsByPage(currentPage)
                }.onFailure {
                    Log.d("aaa", "delete fail")
                }
        }
    }

    private fun reloadProductsByPage(currentPage: Int) {
        productRepository.getPagedCartItems(PAGE_SIZE, currentPage) { result ->
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
                    Log.d("CartViewModel", "increase fail")
                }
        }
    }

    fun decreaseQuantity(productId: Long) {
        cartRepository.decreaseQuantity(productId) { result ->
            result
                .onSuccess {
                    updateQuantity(productId, -1)
                }.onFailure {
                    Log.d("CartViewModel", "decrease fail")
                }
        }
    }

    private fun updateQuantity(
        productId: Long,
        amount: Int,
    ) {
        // TODO: 상품 수량 + - 시, 전체 화면 갱신됨..
        val currentState = _products.value

        if (currentState is ResultState.Success) {
            val updatedList =
                currentState.data.map { item ->
                    if (item.product.productId == productId) {
                        item.copy(quantity = item.quantity + amount)
                    } else {
                        item
                    }
                }
            _products.postValue(ResultState.Success(updatedList))
        }
    }

    override fun onClickPrevious() {
        changePage(next = false)
    }

    override fun onClickNext() {
        changePage(next = true)
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
        private const val PAGE_SIZE = 5
    }
}
