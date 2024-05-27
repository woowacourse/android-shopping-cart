package woowacourse.shopping.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.home.adapter.product.ShoppingItem.ProductItem
import woowacourse.shopping.view.state.UIState

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(), HomeClickListener, QuantityClickListener {
    private val _shoppingUiState = MutableLiveData<UIState<List<ProductItem>>>(UIState.Empty)
    val shoppingUiState: LiveData<UIState<List<ProductItem>>>
        get() = _shoppingUiState

    private val _recentProducts = MutableLiveData<List<RecentProduct>>()
    val recentProducts: LiveData<List<RecentProduct>>
        get() = _recentProducts

    val isRecentProductsEmpty: LiveData<Boolean> =
        recentProducts.map { recentProductsValue ->
            recentProductsValue.isEmpty()
        }

    private val _canLoadMore = MutableLiveData(false)
    val canLoadMore: LiveData<Boolean>
        get() = _canLoadMore

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int>
        get() = _totalQuantity

    private val _updatedProductItem = MutableLiveData<ProductItem>()
    val updatedProductItem: LiveData<ProductItem>
        get() = _updatedProductItem

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val _loadedProductItems = MutableLiveData<List<ProductItem>>(emptyList())
    val loadedProductItems: LiveData<List<ProductItem>>
        get() = _loadedProductItems

    init {
        loadRecentProducts()
        loadProducts()
        loadTotalQuantity()
    }

    private fun loadRecentProducts() {
        _recentProducts.value = recentProductRepository.findAll(RECENT_PRODUCTS_LIMIT)
    }

    private fun loadProducts() {
        try {
            val products = productRepository.findProductsByPage()
            if (products.isEmpty()) {
                _shoppingUiState.value = UIState.Empty
            } else {
                val productItems =
                    products.map { product ->
                        val quantity = cartRepository.productQuantity(product.id)
                        ProductItem(product, quantity)
                    }
                _canLoadMore.value = productRepository.canLoadMore()
                _shoppingUiState.value =
                    UIState.Success(loadedProductItems.value?.plus(productItems) ?: emptyList())
                _loadedProductItems.value = loadedProductItems.value?.plus(productItems)
            }
        } catch (e: Exception) {
            _shoppingUiState.value = UIState.Error(e)
        }
    }

    fun updateData() {
        loadRecentProducts()

        val cartItems = cartRepository.findAll()
        _totalQuantity.value = cartItems.sumOf { cartItem -> cartItem.quantity }

        val updatedProductItems =
            cartItems.map { cartItem ->
                val product = productRepository.findProductById(cartItem.productId) ?: return
                val quantity = cartRepository.productQuantity(product.id)
                ProductItem(product, quantity)
            }

        loadedProductItems.value?.forEachIndexed { index, loadedItem ->
            if (loadedItem.quantity > 0 && cartItems.firstOrNull { it.productId == loadedItem.product.id } == null) {
                val productItem = ProductItem(loadedItem.product, 0)
                (loadedProductItems.value as MutableList)[index] = productItem
            }
        }

        updatedProductItems.forEach { updatedItem ->
            val position =
                loadedProductItems.value?.indexOfFirst { loadedItem ->
                    loadedItem.product.id == updatedItem.product.id
                }
            if (position != null) {
                (loadedProductItems.value as MutableList)[position] = updatedItem
            }
        }
        _loadedProductItems.value = loadedProductItems.value?.toList()
    }

    private fun loadTotalQuantity() {
        _totalQuantity.value = cartRepository.findAll().sumOf { cartItem -> cartItem.quantity }
    }

    override fun onLoadMoreButtonClick() {
        loadProducts()
    }

    override fun onShoppingCartButtonClick() {
        _navigateToCart.value = Event(true)
    }

    override fun onPlusButtonClick(product: Product) {
        val quantity = cartRepository.productQuantity(product.id) + 1
        _totalQuantity.value = totalQuantity.value?.plus(1)
        cartRepository.save(product, quantity)

        updateLoadedProductQuantity(product, quantity)
    }

    private fun updateLoadedProductQuantity(
        product: Product,
        quantity: Int,
    ) {
        val productItem = ProductItem(product, quantity)
        _updatedProductItem.value = productItem

        val position = _loadedProductItems.value?.indexOfFirst { it.product.id == product.id }
        if (position != null) {
            (_loadedProductItems.value as MutableList)[position] = productItem
        }
    }

    override fun onProductClick(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }

    override fun onQuantityPlusButtonClick(productId: Long) {
        val product = productRepository.findProductById(productId) ?: return
        val quantity = cartRepository.productQuantity(product.id) + 1
        _totalQuantity.value = totalQuantity.value?.plus(1)
        cartRepository.save(product, quantity)

        updateLoadedProductQuantity(product, quantity)
    }

    override fun onQuqntityMinusButtonClick(productId: Long) {
        val product = productRepository.findProductById(productId) ?: return
        val cartItem = cartRepository.findOrNullByProductId(productId) ?: return
        val quantity = cartItem.quantity - 1
        _totalQuantity.value = totalQuantity.value?.minus(1)?.coerceAtLeast(0)
        if (quantity == 0) {
            cartRepository.delete(cartItem.id)
        } else {
            cartRepository.save(product, quantity)
        }

        updateLoadedProductQuantity(product, quantity)
    }

    companion object {
        const val PAGE_SIZE = 20
        const val RECENT_PRODUCTS_LIMIT = 10
    }
}
