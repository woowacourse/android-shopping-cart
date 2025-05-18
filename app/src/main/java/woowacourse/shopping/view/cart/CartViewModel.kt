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
    private val _products = MutableLiveData(cartRepository.products)
    val products: LiveData<MutableList<Product>> = _products

    private val _pageCount = MutableLiveData(1)
    val pageCount: LiveData<Int> = _pageCount

    private val _loadedProducts = MutableLiveData<List<Product>>()
    val loadedProducts: LiveData<List<Product>> = _loadedProducts

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    private val _isFirstPage = MutableLiveData<Boolean>()
    val isFirstPage: LiveData<Boolean> = _isFirstPage

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val pageSize = 5

    init {
        loadPage(1)
    }

    fun removeToCart(product: Product) {
        cartRepository.remove(product)
        _products.value = cartRepository.products
        _isOnlyOnePage.value = checkOnlyOnePage()
        loadPage(_pageCount.value ?: 1)
    }

    fun loadNextPage() {
        val nextPage = (_pageCount.value ?: 1) + 1
        val maxPage = ((cartRepository.products.size - 1) / pageSize) + 1
        if (nextPage <= maxPage) {
            loadPage(nextPage)
        }
    }

    fun loadPreviousPage() {
        val prevPage = (_pageCount.value ?: 1) - 1
        if (prevPage >= 1) {
            loadPage(prevPage)
        }
    }

    private fun checkFirstPage(): Boolean = (_pageCount.value == 1)

    private fun checkLastPage(): Boolean {
        val totalPageCount = (cartRepository.products.size + pageSize - 1) / pageSize
        return _pageCount.value == totalPageCount
    }

    private fun checkOnlyOnePage(): Boolean = cartRepository.products.size <= 5

    private fun loadPage(page: Int) {
        val maxPage = ((cartRepository.products.size - 1) / pageSize) + 1
        if (page < 1 || page > maxPage) return

        val start = (page - 1) * pageSize
        val end = minOf(start + pageSize, cartRepository.products.size)

        val items = cartRepository.products.subList(start, end)
        _loadedProducts.postValue(items)
        _pageCount.value = page
        _isOnlyOnePage.value = checkOnlyOnePage()
        _isFirstPage.value = checkFirstPage()
        _isLastPage.value = checkLastPage()
    }

    companion object {
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
