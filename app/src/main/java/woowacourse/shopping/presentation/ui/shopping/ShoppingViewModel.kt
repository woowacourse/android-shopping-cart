package woowacourse.shopping.presentation.ui.shopping

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(
    val shoppingItemsRepository: ShoppingItemsRepository,
    val cartItemsRepository: CartRepository,
) : ViewModel(), ShoppingEventHandler, ShoppingItemCountHandler {
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    private val _shoppingProducts: MutableLiveData<List<ShoppingProduct>> = MutableLiveData()
    val shoppingProducts: LiveData<List<ShoppingProduct>>
        get() = _shoppingProducts

    private val _cartCount = MutableLiveData<Int>(cartItemsRepository.sumOfQuantity())
    val cartCount: LiveData<Int>
        get() = _cartCount

    private val _shoppingUiState = MutableLiveData<UIState<List<Product>>>(UIState.Empty)
    val shoppingUiState: LiveData<UIState<List<Product>>>
        get() = _shoppingUiState

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val numberOfProduct: Int by lazy { shoppingItemsRepository.fetchProductsSize() }

    private val _showLoadMore = MutableLiveData<Boolean>(false)
    val showLoadMore: LiveData<Boolean> = _showLoadMore

    private var offset = 0

    init {
        initializeProducts()
        hideLoadMore()
    }

    fun loadNextProducts() {
        loadProducts()
        hideLoadMore()
    }

    private fun initializeProducts() {
        val nextOffSet = calculateNextOffset()
        val initialProducts = loadProducts(nextOffSet)
        if (nextOffSet != initialProducts.size) throw IllegalStateException("Something went wrong, please try again..")
        offset = nextOffSet
        _products.postValue(initialProducts)
        _shoppingProducts.postValue(convertToShoppingProductList(initialProducts))
    }

    private fun loadProducts(end: Int): List<Product> {
        return shoppingItemsRepository.fetchProductsWithIndex(end = end)
    }

    private fun loadProducts(
        start: Int,
        end: Int,
    ): List<Product> {
        return shoppingItemsRepository.fetchProductsWithIndex(start, end)
    }

    private fun convertToShoppingProductList(products: List<Product>): List<ShoppingProduct> {
        return products.map { createShoppingProduct(it) }
    }

    private fun createShoppingProduct(product: Product): ShoppingProduct {
        val quantity = cartItemsRepository.findQuantityWithProductId(product.id)
        return ShoppingProduct(
            product = product,
            quantity = quantity,
        )
    }

    fun fetchQuantity(productId: Long): Int {
        return cartItemsRepository.findQuantityWithProductId(productId)
    }

    private fun getProducts(): List<Product> {
        val currentOffset = offset
        offset = calculateNextOffset()
        return loadProducts(currentOffset, offset)
    }

    private fun calculateNextOffset(): Int = Integer.min(offset + PAGE_SIZE, numberOfProduct)

    private fun loadProducts() {
        val currentProducts = products.value
        val nextProducts = getProducts()

        if (currentProducts == null) return
        _products.postValue(currentProducts + nextProducts)
        _shoppingProducts.postValue(convertToShoppingProductList(currentProducts + nextProducts))
    }

    fun showLoadMoreByCondition() {
        if (offset != numberOfProduct) showLoadMore()
    }

    fun showLoadMore() {
        _showLoadMore.postValue(true)
    }

    fun hideLoadMore() {
        _showLoadMore.postValue(false)
    }

    override fun onProductClick(productId: Long) {
        _navigateToDetail.postValue(Event(productId))
    }

    override fun updateCartCount() {
        _cartCount.value = cartItemsRepository.sumOfQuantity()
    }

    override fun onLoadMoreButtonClick() {
        loadNextProducts()
    }

    override fun onShoppingCartButtonClick() {
        _navigateToCart.postValue(Event(true))
    }

    override fun increaseCount(productId: Long) {
        val shoppingProduct = _shoppingProducts.value?.find { it.product.id == productId }
        shoppingProduct?.increase()
        val product = shoppingItemsRepository.findProductItem(productId) ?: return

        cartItemsRepository.insert(product, shoppingProduct?.quantity ?: 1)

        // cartItemsRepository.updateQuantityWithProductId(productId, shoppingProduct?.quantity ?: 1)
        _shoppingProducts.value = _shoppingProducts.value
        Log.d("crong", "increaseCount: clicked")
        Log.d("crong", "productId: $productId")
        Log.d("crong", "increaseCount: ${shoppingProduct?.quantity}")
        Log.d("crong", "${cartItemsRepository.findQuantityWithProductId(productId)}")
        // 숫자 업데이트 안되면, count update로직 추가
        updateCartCount()
    }

    override fun decreaseCount(productId: Long) {
        val shoppingProduct = _shoppingProducts.value?.find { it.product.id == productId }
        shoppingProduct?.decrease()
        val quantity = shoppingProduct?.quantity ?: 0

        Log.d("crong", "decreaseCount: clicked")
        Log.d("crong", "productId: $productId")
        Log.d("crong", "decreaseCount: $quantity")
        if (quantity > 0) {
            cartItemsRepository.updateQuantity(productId, shoppingProduct?.quantity ?: 1)
            _shoppingProducts.value = _shoppingProducts.value
        } else {
            cartItemsRepository.deleteWithProductId(productId)
            _shoppingProducts.value = _shoppingProducts.value
        }
        // count 0 일 때, 분기처리

        updateCartCount()
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
