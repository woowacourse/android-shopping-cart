import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.db.CartItemDao
import woowacourse.shopping.model.CartItem

class CartRepository(private val cartItemDao: CartItemDao) {

    private val _allCartItems = MutableLiveData<List<CartItem>>()
    val allCartItems: LiveData<List<CartItem>> = _allCartItems

    init {
        loadAllCartItems()
    }

    private fun loadAllCartItems() {
        _allCartItems.value = cartItemDao.getAllCartItems().map { it.toCartItem() }
    }

    suspend fun insert(cartItem: CartItem) {
        cartItemDao.insert(cartItem.toCartItemEntity())
        loadAllCartItems()
    }

    suspend fun update(cartItem: CartItem) {
        cartItemDao.update(cartItem.toCartItemEntity())
        loadAllCartItems()
    }

    suspend fun delete(cartItem: CartItem) {
        cartItemDao.delete(cartItem.toCartItemEntity())
        loadAllCartItems()
    }

    suspend fun clearCart() {
        cartItemDao.clearCart()
        loadAllCartItems()
    }
}
