package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.Cart
import woowacourse.shopping.data.CartImpl
import woowacourse.shopping.model.products.Product

class CartViewModel(
    private val cart: Cart,
) : ViewModel() {
    private val _productsInCart = MutableLiveData(cart.productsInCart)
    val productsInCart: LiveData<MutableList<Product>> get() = _productsInCart

    private val _pageCount = MutableLiveData(1)
    val pageCount: LiveData<Int> get() = _pageCount

    private val _loadedItems = MutableLiveData<List<Product>>()
    val loadedItems: LiveData<List<Product>> get() = _loadedItems

    private val _backArrowButton = MutableLiveData<Unit>()
    val backArrowButton: LiveData<Unit> get() = _backArrowButton

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    private val _isFirstPage = MutableLiveData<Boolean>()
    val isFirstPage: LiveData<Boolean> get() = _isFirstPage

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private val pageSize = 5

    init {
        loadPage(1)
    }

    fun removeToCart(product: Product) {
        cart.remove(product)
        _productsInCart.value = cart.productsInCart
        _isOnlyOnePage.value = checkOnlyOnePage()
        loadPage(_pageCount.value ?: 1)
    }

    fun loadNextPage() {
        val nextPage = (_pageCount.value ?: 1) + 1
        val maxPage = ((cart.productsInCart.size - 1) / pageSize) + 1
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

    fun onBackClicked() {
        _backArrowButton.value = Unit
    }

    private fun checkFirstPage(pageCount: Int): Boolean = (pageCount == 1)

    private fun checkLastPage(pageCount: Int): Boolean {
        val totalPageCount = (cart.productsInCart.size + pageSize - 1) / pageSize
        return pageCount == totalPageCount
    }

    private fun checkOnlyOnePage(): Boolean = cart.productsInCart.size <= 5

    private fun loadPage(page: Int) {
        val maxPage = ((cart.productsInCart.size - 1) / pageSize) + 1
        if (page < 1 || page > maxPage) return

        val start = (page - 1) * pageSize
        val end = minOf(start + pageSize, cart.productsInCart.size)

        val items = cart.productsInCart.subList(start, end)
        _loadedItems.postValue(items)
        _pageCount.value = page
        _isOnlyOnePage.value = checkOnlyOnePage()
        _isFirstPage.value = checkFirstPage(page)
        _isLastPage.value = checkLastPage(page)
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
                        CartImpl,
                    ) as T
                }
            }
    }
}
