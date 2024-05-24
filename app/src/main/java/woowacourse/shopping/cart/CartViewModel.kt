package woowacourse.shopping.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore
import kotlin.math.min

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val productStore = DummyProductStore()

    private var _productIds: List<Int> = listOf()
    private val productIds: List<Int>
        get() = _productIds

    private var _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage
    private var _itemsInShoppingCartPage: MutableLiveData<MutableList<Product>> = MutableLiveData()

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    init {
        ShoppingCart.initialize(application)
        loadCartItems()
        updateItemsInShoppingCart()
    }

    val itemsInShoppingCartPage: LiveData<MutableList<Product>> get() = _itemsInShoppingCartPage

    private fun loadCartItems() {
        viewModelScope.launch {
            val cartItemsFromDb = ShoppingCart.getCartItems()
            _cartItems.value = cartItemsFromDb
            _productIds = cartItemsFromDb.map { it.productId }
            updateItemsInShoppingCart()
        }
    }

    fun deleteItem(productId: Int) {
        viewModelScope.launch {
            ShoppingCart.deleteProduct(productId)
            _itemsInShoppingCartPage.value =
                _itemsInShoppingCartPage.value?.filter { it.id != productId }?.toMutableList()
            _productIds = _productIds.filter { it != productId }
            loadCartItems()
        }
    }

    fun nextPage() {
        if (productIds.size / COUNT_PER_LOAD < (currentPage.value ?: 0)) return
        _currentPage.value = _currentPage.value?.plus(1)
        updateItemsInShoppingCart()
    }

    fun previousPage() {
        if (currentPage.value == 1) return
        _currentPage.value = _currentPage.value?.minus(1)
        updateItemsInShoppingCart()
    }

    private fun updateItemsInShoppingCart() {
        currentPage.value?.let { page ->
            val endIndex = min(productIds.size, page * COUNT_PER_LOAD)
            val newItems =
                productIds.subList((page - 1) * COUNT_PER_LOAD, endIndex)
                    .map { productId -> productStore.findById(productId) }
            _itemsInShoppingCartPage.value = newItems.toMutableList()
        }
    }

    fun increaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCart.addProductCount(productId)
            loadCartItems()
        }
    }

    fun decreaseQuantity(productId: Int) {
        viewModelScope.launch {
            ShoppingCart.subtractProductCount(productId)
            loadCartItems()
        }
    }

    companion object {
        private const val COUNT_PER_LOAD = 5
    }
}
