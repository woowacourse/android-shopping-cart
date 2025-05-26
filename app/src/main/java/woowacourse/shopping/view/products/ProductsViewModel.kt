package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.products.ProductRepository
import woowacourse.shopping.data.products.ProductRepositoryImpl
import woowacourse.shopping.data.recentProducts.RecentProductsRepository
import woowacourse.shopping.data.recentProducts.RecentProductsRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.model.product.Product
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductsRepository: RecentProductsRepository,
) : ViewModel(),
    QuantityController {
    private val _productsInShop = MutableLiveData<List<CartItem>>()
    val productsInShop: LiveData<List<CartItem>> = _productsInShop

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean> = _isLoadMoreButtonVisible

    private val _navigateToCart = MutableLiveData<Event<Unit>>()
    val navigateToCart: LiveData<Event<Unit>> = _navigateToCart

    private val _cartItemCount = MutableLiveData(INITIAL_CART_ITEM_COUNT)
    val cartItemCount: LiveData<Int> = _cartItemCount

    private val _recentProducts = MutableLiveData<List<Product>>()
    val recentProducts: LiveData<List<Product>> = _recentProducts

    private var isAllProductsFetched = false
    private var currentPage = INITIAL_PAGE
    private val loadedItems = mutableListOf<CartItem>()

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        _productsInShop.postValue(
            _productsInShop.value?.map {
                if (it.product.id == productId) {
                    val newQuantity = it.quantity + quantityIncrease
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            },
        )
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        _productsInShop.postValue(
            _productsInShop.value?.map {
                if (it.product.id == productId && it.quantity > minQuantity) {
                    val newQuantity = it.quantity - quantityDecrease
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            },
        )
    }

    override fun updateQuantity() {
        cartRepository.getAll { cartItems ->
            val allProducts = _productsInShop.value?.toMutableList() ?: return@getAll
            cartItems.forEach { cartItem ->
                val index = allProducts.indexOfFirst { it.product.id == cartItem.product.id }
                val product = allProducts[index]
                if (cartItem.quantity != product.quantity) {
                    allProducts[index] = product.copy(quantity = cartItem.quantity)
                }
            }
            _productsInShop.postValue(allProducts)
            _productsInShop.value?.forEach {
                cartRepository.update(it.product.id, it.quantity)
            }
        }
    }

    private fun setRecentProducts() {
        recentProductsRepository.getAll { recentProducts ->
            _recentProducts.postValue(recentProducts)
        }
    }

    fun loadPage() {
        setRecentProducts()
        setUpdatedProducts()
        val pageSize = PAGE_SIZE
        val nextStart = currentPage * pageSize
        val nextEnd = minOf(nextStart + pageSize, productRepository.getAll().size)

        if (nextStart < productRepository.getAll().size) {
            val nextItems = productRepository.fetchProducts(nextStart, nextEnd)
            loadedItems.addAll(nextItems)
            _productsInShop.postValue(loadedItems.toList())
            currentPage++
            if (nextEnd == productRepository.getAll().size) isAllProductsFetched = true
        }
    }

    fun reloadPage() {
        updateQuantity()
        updateCartItemCount()
        if (_productsInShop.value?.isNotEmpty() == true) return
        loadPage()
    }

    fun updateButtonVisibility(canLoadMore: Boolean) {
        _isLoadMoreButtonVisible.value = canLoadMore && !isAllProductsFetched
    }

    fun onNavigateToCartClicked() {
        _navigateToCart.value = Event(Unit)
    }

    fun onOpenQuantitySelectClick(cartItem: CartItem) {
        cartRepository.add(cartItem) {
            updateCartItemCount()
        }
    }

    fun addRecentProduct(cartItem: CartItem) {
        val recentProduct = cartItem.product
        recentProductsRepository.add(recentProduct)
        _recentProducts.value = _recentProducts.value?.plus(cartItem.product)
    }

    private fun updateCartItemCount() {
        cartRepository.getAll { cartItems ->
            _cartItemCount.postValue(cartItems.size)
        }
    }

    private fun setUpdatedProducts() {
        val updatedList = mutableListOf<CartItem>()
        var completedCount = 0

        if (loadedItems.isEmpty()) {
            _productsInShop.postValue(emptyList())
            return
        }

        loadedItems.forEach { cartItem ->
            cartRepository.findQuantityById(cartItem.product.id) { quantity ->
                val updated = cartItem.copy(quantity = quantity)

                synchronized(updatedList) {
                    updatedList.add(updated)
                    completedCount++

                    if (completedCount == loadedItems.size) {
                        _productsInShop.postValue(updatedList)
                    }
                }
            }
        }
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val PAGE_SIZE = 20
        private const val INITIAL_CART_ITEM_COUNT = 0

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val context = application.applicationContext

                    val database = ShoppingDatabase.getDatabase(context)
                    val cartDao = database.cartDao()
                    val recentProductDao = database.recentProductDao()
                    extras.createSavedStateHandle()

                    return ProductsViewModel(
                        ProductRepositoryImpl(),
                        CartRepositoryImpl(cartDao),
                        RecentProductsRepositoryImpl(recentProductDao),
                    ) as T
                }
            }
    }
}
