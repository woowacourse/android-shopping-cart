package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.product.Product

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _currentPageNumber = MutableLiveData(INITIAL_PAGE)
    val currentPageNumber: LiveData<Int> = _currentPageNumber

    private val _loadedProducts = MutableLiveData<List<Product>>()
    val loadedProducts: LiveData<List<Product>> = _loadedProducts

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    private val _isFirstPage = MutableLiveData<Boolean>()
    val isFirstPage: LiveData<Boolean> = _isFirstPage

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> = _isLastPage

    init {
        loadPage(INITIAL_PAGE)
    }

    fun removeFromCart(product: Product) {
        cartRepository.remove(product)
        _loadedProducts.value = cartRepository.getAll()
        if (!existPage()) _currentPageNumber.value = minusPageNumber()

        loadPage(_currentPageNumber.value ?: INITIAL_PAGE)
    }

    fun loadNextPage() {
        val nextPage = plusPageNumber()
        val maxPage = getMaxPageNumber()
        if (nextPage <= maxPage) {
            loadPage(nextPage)
        }
    }

    fun loadPreviousPage() {
        val prevPage = minusPageNumber()
        if (prevPage >= INITIAL_PAGE) {
            loadPage(prevPage)
        }
    }

    private fun getMaxPageNumber(): Int = ((cartRepository.getAll().size - ONE_PAGE_COUNT) / PAGE_SIZE) + ONE_PAGE_COUNT

    private fun minusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) - ONE_PAGE_COUNT

    private fun plusPageNumber(): Int = (_currentPageNumber.value ?: INITIAL_PAGE) + ONE_PAGE_COUNT

    private fun checkFirstPage(): Boolean = (_currentPageNumber.value == INITIAL_PAGE)

    private fun checkLastPage(): Boolean {
        val totalPageNumber = getMaxPageNumber()
        return _currentPageNumber.value == totalPageNumber
    }

    private fun checkOnlyOnePage(): Boolean = cartRepository.getAll().size <= PAGE_SIZE

    private fun existPage(): Boolean {
        val maxPageNumber = getMaxPageNumber()
        return (_currentPageNumber.value ?: INITIAL_PAGE) <= maxPageNumber
    }

    private fun loadPage(page: Int) {
        val start = (page - ONE_PAGE_COUNT) * PAGE_SIZE
        val end = minOf(start + PAGE_SIZE, cartRepository.getAll().size)

        val items = cartRepository.fetchProducts(start, end)
        _loadedProducts.postValue(items)
        _currentPageNumber.value = page
        _isOnlyOnePage.value = checkOnlyOnePage()
        _isFirstPage.value = checkFirstPage()
        _isLastPage.value = checkLastPage()
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 1
        private const val ONE_PAGE_COUNT = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return CartViewModel(
                        CartRepositoryImpl,
                    ) as T
                }
            }
    }
}
