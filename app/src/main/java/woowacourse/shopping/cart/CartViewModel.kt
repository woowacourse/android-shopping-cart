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
    private val loadItemsInCartPage = currentPage.value?.let {
        val offset = min(productIds.size, (it) * COUNT_PER_LOAD)

        productIds.subList((it - 1) * COUNT_PER_LOAD, offset)
            .map { productId -> productStore.findById(productId) }
            .toMutableList()
    }
    private var _itemsInShoppingCartPage: MutableLiveData<MutableList<Product>> =
        MutableLiveData(loadItemsInCartPage)
    val itemsInShoppingCartPage: LiveData<MutableList<Product>> get() = _itemsInShoppingCartPage

    fun deleteItem(productId: Int) {
        ShoppingCart.delete(productId)
        _itemsInShoppingCartPage.value =
            _itemsInShoppingCartPage.value?.filter { it.id != productId }?.toMutableList()
        _productIds = ShoppingCart.productIds
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
            val newItems = productIds.subList((page - 1) * COUNT_PER_LOAD, endIndex)
                .map { productId -> productStore.findById(productId) }
            _itemsInShoppingCartPage.value = newItems.toMutableList()
        }
    }

    companion object {
        private const val COUNT_PER_LOAD = 5
    }
}
