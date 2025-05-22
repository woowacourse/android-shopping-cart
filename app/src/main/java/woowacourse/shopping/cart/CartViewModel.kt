package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class CartViewModel(
    private val repository: CartItemRepository,
) : ViewModel(),
    CartEventHandler {
    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private var currentPage: Int = INITIAL_PAGE

    private val _pageEvent = SingleLiveEvent<Int>()
    val pageEvent: LiveData<Int> = _pageEvent

    init {
        loadCartProducts()
    }

    override fun onDeleteProduct(cartProduct: ProductUiModel) {
        thread {
            repository.deleteCartItemById(cartProduct.id)
            loadCartProducts()
        }
    }

    override fun onNextPage() {
        thread {
            val lastPage = (repository.allCartItemSize - 1) / PAGE_SIZE
            if (currentPage < lastPage) {
                currentPage++
                _pageEvent.postValue(currentPage)
                loadCartProducts()
            }
        }
    }

    override fun onPrevPage() {
        thread {
            if (currentPage > 0) {
                currentPage--
                _pageEvent.postValue(currentPage)
                loadCartProducts()
            }
        }
    }

    override fun isNextButtonEnabled(): Boolean = _isNextButtonEnabled.value == true

    override fun isPrevButtonEnabled(): Boolean = _isPrevButtonEnabled.value == true

    override fun isPaginationEnabled(): Boolean = isNextButtonEnabled() || isPrevButtonEnabled()

    override fun getPage(): Int = currentPage

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        thread {
            var current = currentPage
            val totalSize = repository.allCartItemSize

            while (current > 0 && current * pageSize >= totalSize) {
                current--
            }

            currentPage = current
            _pageEvent.postValue(current)

            val startIndex = current * pageSize
            val endIndex = minOf(startIndex + pageSize, totalSize)

            _cartProducts.postValue(repository.subListCartItems(startIndex, endIndex))
            _isNextButtonEnabled.postValue(current < (totalSize - 1) / pageSize)
            _isPrevButtonEnabled.postValue(current > 0)
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0

        fun factory(repository: CartItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                        return CartViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
