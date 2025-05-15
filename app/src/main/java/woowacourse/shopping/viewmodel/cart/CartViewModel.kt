package woowacourse.shopping.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.Cart

class CartViewModel(
    private val cart: Cart,
) : ViewModel() {
    private val _productsInCart = MutableLiveData(cart.productsInCart)
    val productsInCart: LiveData<MutableList<Product>> get() = _productsInCart

    private val _pageCount = MutableLiveData(1)
    val pageCount: LiveData<Int> get() = _pageCount

    private val _loadedItems = MutableLiveData<List<Product>>()
    val loadedItems: LiveData<List<Product>> get() = _loadedItems
    private val pageSize = 5

    init {
        loadPage(1)
    }

    fun removeToCart(product: Product) {
        cart.remove(product)
        _productsInCart.value = cart.productsInCart
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

    fun isFirstPage(pageCount: Int): Boolean = (pageCount == 1)

    fun isLastPage(pageCount: Int): Boolean {
        val totalPageCount = (cart.productsInCart.size + pageSize - 1) / pageSize
        return pageCount == totalPageCount
    }

    fun isOnlyOnePage(): Boolean = cart.productsInCart.size <= 5

    private fun loadPage(page: Int) {
        val maxPage = ((cart.productsInCart.size - 1) / pageSize) + 1
        if (page < 1 || page > maxPage) return

        val start = (page - 1) * pageSize
        val end = minOf(start + pageSize, cart.productsInCart.size)

        val items = cart.productsInCart.subList(start, end)
        _loadedItems.postValue(items)
        _pageCount.value = page
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
                        Cart,
                    ) as T
                }
            }
    }
}
