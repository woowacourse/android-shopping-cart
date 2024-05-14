package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.db.Product
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.repository.DummyProductStore

class CartViewModel : ViewModel() {
    private val productStore = DummyProductStore()

    private val productIds: List<Int> = ShoppingCart.productIds

    private val _itemsInShoppingCart: MutableLiveData<List<Product>> =
        MutableLiveData(productIds.map { productId -> productStore.findById(productId) })
    val itemsInShoppingCart: LiveData<List<Product>> get() = _itemsInShoppingCart

    fun deleteItem(productId: Int) {
        ShoppingCart.delete(productId)
        _itemsInShoppingCart.value = _itemsInShoppingCart.value?.filter { it.id != productId }
    }
}
