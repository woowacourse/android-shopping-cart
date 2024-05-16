package woowacourse.shopping.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.repository.DummyProductStore

class CartViewModel : ViewModel() {
    private val productStore = DummyProductStore()

    private val productIds: List<Int> = ShoppingCart.productIds

    private var _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int> get() = _currentPage


    private val _itemsInShoppingCart: MutableLiveData<List<Product>> =
        MutableLiveData(productIds.map { productId -> productStore.findById(productId) })
    val itemsInShoppingCart: LiveData<List<Product>> get() = _itemsInShoppingCart

    fun deleteItem(productId: Int) {
        ShoppingCart.delete(productId)
        _itemsInShoppingCart.value = _itemsInShoppingCart.value?.filter { it.id != productId }
    }

    fun nextPage() {
        if (productIds.size / 3 < (currentPage.value ?: 0)) return

        _currentPage.value = _currentPage.value?.plus(1)
        Log.d(TAG, "nextPage: ${currentPage.value}")
    }

    fun previousPage() {
        if (currentPage.value == 1) return
        _currentPage.value = _currentPage.value?.minus(1)
        Log.d(TAG, "previousPage: ${currentPage.value}")
    }

    companion object {
        private const val TAG = "CartViewModel"
    }
}
