package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.data.remote.DummyRecentRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.fromProductsAndCarts
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentProductItem
import woowacourse.shopping.domain.RecentRepository
import woowacourse.shopping.presentation.ui.Error
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class ShoppingViewModel(
    private val productRepository: ProductRepository = DummyProductRepository(),
    private val recentRepository: RecentRepository = DummyRecentRepository,
    private val cartRepository: CartRepository = DummyCartRepository,
) :
    ViewModel() {
    private var currentPage: Int = 0

    private val _cartProducts = MutableLiveData<UiState<List<Cart>>>(UiState.None)

    val cartProducts: LiveData<UiState<List<Cart>>> get() = _cartProducts

    private val _recentProducts = MutableLiveData<UiState<List<RecentProductItem>>>(UiState.None)

    val recentProducts: LiveData<UiState<List<RecentProductItem>>> get() = _recentProducts

    private val _shoppingProducts =
        MutableLiveData<UiState<List<ProductListItem.ShoppingProductItem>>>(UiState.None)

    val shoppingProducts: LiveData<UiState<List<ProductListItem.ShoppingProductItem>>> get() = _shoppingProducts

    private val shoppingProductItems = mutableListOf<ProductListItem.ShoppingProductItem>()

    private val _error = MutableLiveData<Event<Error>>()

    val error: LiveData<Event<Error>> get() = _error

    fun loadInitialShoppingItems() {
        if (shoppingProducts.value !is UiState.Success<List<ProductListItem.ShoppingProductItem>>) {
            fetchInitialRecentProducts()
            fetchInitialCartProducts()
        }
    }

    private fun fetchInitialRecentProducts() {
        recentRepository.load().onSuccess {
            _recentProducts.value = UiState.Success(it)
        }.onFailure {
            _error.value = Event(Error.RecentProductItemsNotFound)
        }
    }

    private fun fetchInitialCartProducts() {
        cartRepository.loadAll().onSuccess {
            _cartProducts.value = UiState.Success(it)
            fetchInitialProducts(it)
        }.onFailure {
            _error.value = Event(Error.CartItemsNotFound)
        }
    }

    private fun fetchInitialProducts(carts: List<Cart>) {
        productRepository.load(currentPage, PAGE_SIZE).onSuccess { products ->
            currentPage++
            addShoppingProducts(products, carts)
        }.onFailure {
            _error.value = Event(Error.ProductItemsNotFound)
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
            _error.value = Event(Error.AllProductsLoaded)
        }
    }

    private fun addShoppingProducts(
        products: List<Product>,
        carts: List<Cart>,
    ) {
        val newShoppingProducts = fromProductsAndCarts(products, carts)
        shoppingProductItems.addAll(newShoppingProducts)
        _shoppingProducts.value = UiState.Success(shoppingProductItems)
    }

    fun updateCartItemQuantity(
        product: Product,
        quantityDelta: Int,
    ) {
        cartRepository.updateQuantity(product, quantityDelta).onSuccess {
            modifyShoppingProductQuantity(product.id, quantityDelta)
        }
    }

    fun modifyShoppingProductQuantity(
        productId: Long,
        quantityDelta: Int,
    ) {
        val productIndex = shoppingProductItems.indexOfFirst { it.id == productId }
        val originalQuantity = shoppingProductItems[productIndex].quantity
        val updatedProduct =
            shoppingProductItems[productIndex].copy(quantity = originalQuantity + quantityDelta)
        shoppingProductItems[productIndex] = updatedProduct
        _shoppingProducts.value = UiState.Success(shoppingProductItems)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
