package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.products.ProductRepository
import woowacourse.shopping.data.products.ProductRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.Event

class ProductsViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _productsInShop = MutableLiveData<List<CartItem>>()
    val productsInShop: LiveData<List<CartItem>> = _productsInShop

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean> = _isLoadMoreButtonVisible

    private val _navigateToCart = MutableLiveData<Event<Unit>>()
    val navigateToCart: LiveData<Event<Unit>> = _navigateToCart

//    private val _quantities = MutableLiveData<Map<Long, Int>>()
//    val quantities: LiveData<Map<Long, Int>> = _quantities

    private var isAllProductsFetched = false
    private var currentPage = INITIAL_PAGE
    private val loadedItems = mutableListOf<CartItem>()

    init {
        loadPage()
    }

    fun loadPage() {
        val pageSize = PAGE_SIZE
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, productRepository.getAll().size)

        if (nextStart < productRepository.getAll().size) {
            val nextItems = productRepository.fetchProducts(nextStart, nextEnd)
            loadedItems.addAll(nextItems)
            _productsInShop.value = loadedItems.toList()
            currentPage++
            if (nextEnd == productRepository.getAll().size) isAllProductsFetched = true
        }
    }

    fun updateButtonVisibility(canLoadMore: Boolean) {
        _isLoadMoreButtonVisible.value = canLoadMore && !isAllProductsFetched
    }

    fun onNavigateToCartClicked() {
        _navigateToCart.value = Event(Unit)
    }

    fun increaseQuantity(productId: Long) {
        _productsInShop.value =
            _productsInShop.value?.map {
                if (it.product.id == productId) {
                    it.copy(quantity = it.quantity + 1)
                } else {
                    it
                }
            }

//        val quantities = _quantities.value ?: emptyMap()
//        val currentQuantity = quantities[productId] ?: 1
//        _quantities.value =
//            quantities.toMutableMap().apply {
//                put(productId, currentQuantity + 1)
//            }
    }

    fun decreaseQuantity(productId: Long) {
        _productsInShop.value =
            _productsInShop.value?.map {
                if (it.product.id == productId && it.quantity > 1) {
                    it.copy(quantity = it.quantity - 1)
                } else {
                    it
                }
            }
//        val currentMap = _quantities.value ?: emptyMap()
//        val currentQuantity = currentMap[productId] ?: 1
//        if (currentQuantity > 1) {
//            _quantities.value =
//                currentMap.toMutableMap().apply {
//                    put(productId, currentQuantity - 1)
//                }
//        }
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return ProductsViewModel(
                        ProductRepositoryImpl(),
                    ) as T
                }
            }
    }
}
