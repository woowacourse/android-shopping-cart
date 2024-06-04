package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.dummy.DummyProductRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.fromProductsAndCarts
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentRepository
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class ShoppingViewModel(
    private val productRepository: ProductRepository = DummyProductRepository(),
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ShoppingHandler {
    private var currentPage: Int = 0

    private val _cartProducts = MutableLiveData<UiState<List<Cart>>>(UiState.None)

    val cartProducts: LiveData<UiState<List<Cart>>> get() = _cartProducts

    private val _cartItemQuantity = MutableLiveData<Int>()
    val cartItemQuantity: LiveData<Int> get() = _cartItemQuantity

    private val _recentProducts = MutableLiveData<UiState<List<RecentProductItem>>>(UiState.None)

    val recentProducts: LiveData<UiState<List<RecentProductItem>>> get() = _recentProducts

    private val _shoppingProducts =
        MutableLiveData<UiState<List<ProductListItem.ShoppingProductItem>>>(UiState.None)

    val shoppingProducts: LiveData<UiState<List<ProductListItem.ShoppingProductItem>>> get() = _shoppingProducts

    private val _error = MutableLiveData<Event<ShoppingError>>()

    val error: LiveData<Event<ShoppingError>> get() = _error

    private val _moveEvent = MutableLiveData<Event<FromShoppingToScreen>>()

    val moveEvent: LiveData<Event<FromShoppingToScreen>> get() = _moveEvent

    fun loadInitialShoppingItems() {
        if (shoppingProducts.value !is UiState.Success<List<ProductListItem.ShoppingProductItem>>) {
            fetchInitialRecentProducts()
            fetchInitialCartProducts()
        }
    }

    fun fetchInitialRecentProducts() {
        recentRepository.loadAll().onSuccess {
            _recentProducts.value = UiState.Success(it)
        }.onFailure {
            _error.value = Event(ShoppingError.RecentProductItemsNotFound)
        }
    }

    fun fetchCartProducts() {
        cartRepository.loadAll().onSuccess { cartItems ->
            _cartProducts.value = UiState.Success(cartItems)
            val totalCartItemsQuantity = cartItems.sumOf { cartItem -> cartItem.quantity }
            _cartItemQuantity.value = totalCartItemsQuantity
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemsNotFound)
        }
    }

    private fun fetchInitialCartProducts() {
        cartRepository.loadAll().onSuccess { cartItems ->
            _cartProducts.value = UiState.Success(cartItems)
            val totalCartItemsQuantity = cartItems.sumOf { cartItem -> cartItem.quantity }
            _cartItemQuantity.value = totalCartItemsQuantity
            fetchInitialProducts(cartItems)
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemsNotFound)
        }
    }

    private fun fetchCartData() {
        cartRepository.loadAll().onSuccess { cartItems ->
            _cartProducts.value = UiState.Success(cartItems)
            val totalCartItemsQuantity = cartItems.sumOf { cartItem -> cartItem.quantity }
            _cartItemQuantity.value = totalCartItemsQuantity
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemsNotFound)
        }
    }

    private fun fetchInitialProducts(carts: List<Cart>) {
        productRepository.load(currentPage, PAGE_SIZE).onSuccess { products ->
            currentPage++
            addShoppingProducts(products, carts)
        }.onFailure {
            _error.value = Event(ShoppingError.ProductItemsNotFound)
        }
    }

    fun fetchProductForNewPage() {
        productRepository.load(currentPage, PAGE_SIZE).onSuccess { products ->
            currentPage++
            if (_cartProducts.value is UiState.Success<List<Cart>>) {
                val carts = (_cartProducts.value as UiState.Success<List<Cart>>).data.map { it }
                addShoppingProducts(products, carts)
            }
        }.onFailure {
            _error.value = Event(ShoppingError.AllProductsLoaded)
        }
    }

    private fun addShoppingProducts(
        products: List<Product>,
        carts: List<Cart>,
    ) {
        val newShoppingProducts = fromProductsAndCarts(products, carts)
        val originShoppingProducts = when (val currentState = _shoppingProducts.value) {
            is UiState.Success -> currentState.data
            else -> emptyList()
        }
        _shoppingProducts.value = UiState.Success(originShoppingProducts + newShoppingProducts)
    }

    private fun updateCartItemQuantity(
        product: Product,
        quantityDelta: Int,
    ) {
        cartRepository.updateQuantity(product, quantityDelta).onSuccess {
            fetchCartData()
            updateShoppingProductQuantity(product.id, quantityDelta)
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemsNotModified)
        }
    }

    private fun updateShoppingProductQuantity(
        productId: Long,
        quantityDelta: Int,
    ) {
        val originShoppingProducts =
            (_shoppingProducts.value as UiState.Success).data
        val targetIndex = originShoppingProducts.indexOfFirst { it.id == productId }
        val originQuantity = originShoppingProducts[targetIndex].quantity
        val newProduct =
            originShoppingProducts[targetIndex].copy(quantity = originQuantity + quantityDelta)
        val newShoppingProducts = originShoppingProducts.toMutableList()
        newShoppingProducts[targetIndex] = newProduct
        _shoppingProducts.value = UiState.Success(newShoppingProducts)
    }

    fun reloadModifiedItems(modifiedProductIds: LongArray, newQuantities: IntArray) {
        modifiedProductIds.zip(newQuantities.toList()).forEach { (id, quantity) ->
            updateProductQuantity(id, quantity)
        }
    }

    fun updateProductQuantity(
        productId: Long,
        newQuantity: Int,
    ) {
        val originShoppingProducts =
            (_shoppingProducts.value as UiState.Success).data
        val targetIndex = originShoppingProducts.indexOfFirst { it.id == productId }
        if (targetIndex != -1) {
            val newProduct =
                originShoppingProducts[targetIndex].copy(quantity = newQuantity)
            val newShoppingProducts = originShoppingProducts.toMutableList()
            newShoppingProducts[targetIndex] = newProduct
            _shoppingProducts.value = UiState.Success(newShoppingProducts)
        }
    }

    override fun onProductItemClick(productId: Long) {
        _moveEvent.value = Event(FromShoppingToScreen.ProductDetail(productId))
    }

    override fun onCartMenuItemClick() {
        _moveEvent.value = Event(FromShoppingToScreen.Cart)
    }

    override fun onLoadMoreClick() {
        fetchProductForNewPage()
    }

    override fun onDecreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            updateCartItemQuantity(
                item.toProduct(),
                -1,
            )
        }
    }

    override fun onIncreaseQuantity(item: ProductListItem.ShoppingProductItem?) {
        item?.let {
            updateCartItemQuantity(
                item.toProduct(),
                1,
            )
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
