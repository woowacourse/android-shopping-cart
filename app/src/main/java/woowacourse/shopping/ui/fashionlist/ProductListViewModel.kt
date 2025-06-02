package woowacourse.shopping.ui.fashionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel(), QuantityClickListener {

    private val _products = MutableLiveData<List<ProductListViewType>>(emptyList())
    val products: LiveData<List<ProductListViewType>> = _products

    private var pageNumber = 0

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _isButtonVisible = MutableLiveData(true)
    val isButtonVisible: LiveData<Boolean> = _isButtonVisible

    private val _recentProducts = MutableLiveData<List<Long>>(emptyList())
    val recentProducts: LiveData<List<Long>> = _recentProducts

    private val _navigateToDetail = MutableLiveData<Product>()
    val navigateToDetail: LiveData<Product> = _navigateToDetail

    init {
        loadProducts()
        loadCartItems()
        loadRecentProducts()
    }

    fun loadProducts() {
        _products.value = productRepository.getProductPagedItems(pageNumber++, 20).map { ProductListViewType.FashionProductItemType(it) }
    }

    fun loadCartItems() {
        cartRepository.getPagedItems(pageNumber++, 20) {
            _cartItems.postValue(it)
        }
    }

    fun loadRecentProducts() {
        historyRepository.getRecentProducts(10) {
            _recentProducts.postValue(it)
        }
    }

    fun addRecentProduct(id: Long) {
        historyRepository.insert(id) {
            _recentProducts.postValue(listOf(it))
        }
    }

    fun onFloatingButtonClick(product: Product) {
        _isButtonVisible.value = false
        val cartItem = CartItem(product.id, product, 0)
        onClickIncrease(cartItem)
    }

    fun onClickLoadMore() {
        loadProducts()
    }

    private fun updateQuantity(cartItem: CartItem, newItem: CartItem) {
        cartRepository.upsert(newItem) {
            val oldItems = _cartItems.value?.toMutableList() ?: throw IllegalArgumentException("")
            val index = oldItems.indexOf(cartItem)
            if (index == -1) {
                oldItems.add(cartItem)
            } else {
                oldItems[index] = newItem
            }
            _cartItems.postValue(oldItems.toList())
        }
    }

    override fun onClickIncrease(cartItem: CartItem) {
        val newItem = cartItem.copy(quantity = cartItem.quantity + 1)
        updateQuantity(cartItem, newItem)
    }

    override fun onClickDecrease(cartItem: CartItem) {
        if (cartItem.quantity == 1) {
            _isButtonVisible.value = true
            cartRepository.removeById(cartItem.id) {
                val oldItems = _cartItems.value?.toMutableList()
                oldItems?.remove(cartItem)
                _cartItems.postValue(oldItems?.toList())
            }
            return
        }
        val newItem = cartItem.copy(quantity = (cartItem.quantity - 1).coerceAtLeast(1))
        updateQuantity(cartItem, newItem)
    }
}
