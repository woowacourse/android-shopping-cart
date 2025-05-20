package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.providers.RepositoryProvider

class CartViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CartProduct>>(emptyList())
    val products: LiveData<List<CartProduct>> get() = _products

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> get() = _pageNumber

    val isFirstPage: Boolean
        get() = pageNumber.value == 1

    var isLastPage: Boolean = false
        private set

    init {
        update()
    }

    fun update() {
        val pageNumber = pageNumber.value ?: 1
        repository.fetchInRange(PAGE_FETCH_SIZE, (pageNumber - 1) * PAGE_SIZE) { products ->
            isLastPage = products.size != PAGE_FETCH_SIZE

            val visibleProductsSize = PAGE_SIZE.coerceAtMost(products.size)

            if (visibleProductsSize == 0) {
                if (isFirstPage) {
                    _products.postValue(emptyList())
                } else {
                    moveToPrevious()
                }
            } else {
                _products.postValue(products.take(visibleProductsSize))
            }
        }
    }

    fun isVisiblePagination(): Boolean {
        return !(isLastPage && pageNumber.value == 1)
    }

    fun moveToPrevious() {
        if (pageNumber.value != 1) {
            _pageNumber.postValue(_pageNumber.value?.minus(1))
        }
    }

    fun moveToNext() {
        if (!isLastPage) {
            _pageNumber.value = _pageNumber.value?.plus(1)
        }
    }

    fun deleteProduct(cartProduct: CartProduct) {
        repository.delete(cartProduct.id!!) {
            update()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CartViewModel(RepositoryProvider.provideCartRepository()) as T
                }
            }
        private const val PAGE_FETCH_SIZE = 6
        private const val PAGE_SIZE = 5
    }
}
