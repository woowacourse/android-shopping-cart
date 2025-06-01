package woowacourse.shopping.view.products

import android.util.Log
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
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityListener
import woowacourse.shopping.view.ToastMessageHandler

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductsRepository: RecentProductsRepository,
) : ViewModel(),
    QuantityListener,
    ToastMessageHandler {
    private val _productsInShop = MutableLiveData<List<CartItem>>()
    val productsInShop: LiveData<List<CartItem>> = _productsInShop

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean> = _isLoadMoreButtonVisible

    private val _navigateToCart = MutableLiveData<Event<Unit>>()
    val navigateToCart: LiveData<Event<Unit>> = _navigateToCart

    private val _cartItemCount = MutableLiveData(INITIAL_CART_ITEM_COUNT)
    val cartItemCount: LiveData<Int> = _cartItemCount

    private val _recentProducts = MutableLiveData<List<CartItem>>()
    val recentProducts: LiveData<List<CartItem>> = _recentProducts

    private val _toastMessage = MutableLiveData<Event<Unit>>()
    override val toastMessage: LiveData<Event<Unit>> = _toastMessage

    private var isAllProductsFetched = false
    private var currentPage = INITIAL_PAGE
    private val loadedItems = mutableListOf<CartItem>()

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        _productsInShop.value =
            _productsInShop.value?.map {
                if (it.product.id == productId) {
                    val newQuantity = it.quantity + quantityIncrease
                    val increasedProduct = it.copy(quantity = newQuantity)
                    updateRecentProduct(increasedProduct)
                    increasedProduct
                } else {
                    it
                }
            }
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        _productsInShop.value =
            _productsInShop.value?.map {
                if (it.product.id == productId && it.quantity > minQuantity) {
                    val newQuantity = it.quantity - quantityDecrease
                    val decreasedProduct = it.copy(quantity = newQuantity)
                    updateRecentProduct(decreasedProduct)
                    decreasedProduct
                } else {
                    it
                }
            }
    }

    override fun updateQuantity() {
        cartRepository.getAll { result ->
            val allProducts = productRepository.getAll().toMutableList()
            result
                .onSuccess { cartItems ->
                    cartItems.forEach { cartItem ->
                        val index =
                            allProducts.indexOfFirst { cartItem.product.id == it.product.id }
                        allProducts[index] = cartItem
                    }
                    _productsInShop.postValue(allProducts)
                    _productsInShop.value?.forEach {
                        cartRepository.update(it.product.id, it.quantity) { result ->
                            result
                                .onSuccess {
                                    return@update
                                }.onFailure {
                                    _toastMessage.postValue(Event(Unit))
                                }
                        }
                    }
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    fun loadPage() {
        setUpdatedProducts()
        val pageSize = PAGE_SIZE
        val nextStart = currentPage * pageSize
        val totalProductsSize = productRepository.getAllSize()
        val nextEnd = minOf(nextStart + pageSize, totalProductsSize)

        if (nextStart < totalProductsSize) {
            val nextItems = productRepository.fetchProducts(nextStart, nextEnd)
            loadedItems.addAll(nextItems)
            _productsInShop.postValue(loadedItems.toList())
            currentPage++
            if (nextEnd == totalProductsSize) isAllProductsFetched = true
        }
    }

    fun reloadPage() {
        updateQuantity()
        updateCartItemCount()
        setRecentProducts()
        if (_productsInShop.value?.isNotEmpty() == true) return
        loadPage()
    }

    fun updateButtonVisibility(canLoadMore: Boolean) {
        _isLoadMoreButtonVisible.value = canLoadMore && !isAllProductsFetched
    }

    fun onNavigateToCartClicked() {
        _navigateToCart.value = Event(Unit)
    }

    fun openQuantitySelectAndAddToCart(cartItem: CartItem) {
        cartRepository.add(cartItem) { result ->
            result
                .onSuccess {
                    updateCartItemCount()
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    fun addOrUpdateRecentProduct(cartItem: CartItem) {
        recentProductsRepository.add(cartItem) { result ->
            result
                .onSuccess {
                    _recentProducts.postValue(_recentProducts.value?.plus(cartItem))
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    private fun setRecentProducts() {
        recentProductsRepository.getAll { result ->
            result
                .onSuccess { recentProducts ->
                    _recentProducts.postValue(recentProducts)
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
        }
    }

    private fun updateRecentProduct(cartItem: CartItem) {
        val currentProducts = _recentProducts.value?.toMutableList() ?: mutableListOf()
        val index = currentProducts.indexOfFirst { it.product.id == cartItem.product.id }

        if (index != -1) {
            val updatedProduct = currentProducts[index].copy(quantity = cartItem.quantity)
            currentProducts[index] = updatedProduct
        } else {
            currentProducts.add(cartItem)
        }

        _recentProducts.value = currentProducts
    }

    private fun updateCartItemCount() {
        cartRepository.getAll { result ->
            result
                .onSuccess {
                    _cartItemCount.postValue(it.size)
                }.onFailure {
                    Log.d("TAG", "fail: $it")
                    _toastMessage.postValue(Event(Unit))
                }
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
            cartRepository.findQuantityById(cartItem.product.id) { result ->
                result
                    .onSuccess { quantity ->
                        val updated = cartItem.copy(quantity = quantity)

                        synchronized(updatedList) {
                            updatedList.add(updated)
                            completedCount++

                            if (completedCount == loadedItems.size) {
                                _productsInShop.postValue(updatedList)
                            }
                        }
                    }.onFailure {
                        Log.d("TAG", "fail: $it")
                        _toastMessage.postValue(Event(Unit))
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
