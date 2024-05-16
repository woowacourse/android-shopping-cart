package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.repository.DummyProductStore
import kotlin.math.min

class CartViewModel : ViewModel() {
    private val productStore = DummyProductStore()

    private var _productIds: List<Int> = ShoppingCart.productIds
    private val productIds: List<Int>
        get() = _productIds

    private var _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInShoppingCartPage: MutableLiveData<MutableList<Product>> =
        MutableLiveData(currentPage.value?.let {
            val endIndex = min(productIds.size, (it) * 5)
            productIds.subList((it - 1) * 5, endIndex)
                .map { productId -> productStore.findById(productId) }.toMutableList()
        })
    val itemsInShoppingCartPage: LiveData<MutableList<Product>> get() = _itemsInShoppingCartPage

    fun deleteItem(productId: Int) {
        ShoppingCart.delete(productId)
        _itemsInShoppingCartPage.value =
            _itemsInShoppingCartPage.value?.filter { it.id != productId }?.toMutableList()
        _productIds = ShoppingCart.productIds
    }

    fun nextPage() {
        if (productIds.size / 5 < (currentPage.value ?: 0)) return
        _currentPage.value = _currentPage.value?.plus(1)
        updateItemsInShoppingCart()
    }

    fun updateItemsInShoppingCart() {
        _itemsInShoppingCartPage.value?.clear()
        currentPage.value?.let {
            val endIndex = min(productIds.size, (it) * 5)
            productIds.subList((it - 1) * 5, endIndex).toMutableList()
                .map { productId ->
                    productStore.findById(productId)
                }
        }?.let {
            _itemsInShoppingCartPage.value?.addAll(it)
                ?: _itemsInShoppingCartPage.postValue(it.toMutableList())
        }

    }

    fun previousPage() {
        if (currentPage.value == 1) return
        _currentPage.value = _currentPage.value?.minus(1)
    }
}
