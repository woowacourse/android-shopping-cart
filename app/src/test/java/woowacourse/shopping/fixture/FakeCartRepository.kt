package woowacourse.shopping.fixture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts

class FakeCartRepository : CartRepository {
    private val cartList = mutableListOf<Cart>()
    private val cartLiveData = MutableLiveData<List<Cart>>()
    private val sizeLiveData = MutableLiveData<Int>()
    var savedCart: Cart? = null

    init {
        updateLiveData()
    }

    private fun updateLiveData() {
        cartLiveData.value = cartList.toList()
        sizeLiveData.value = cartList.size
    }

    override fun insert(cart: Cart) {
        cartList.add(cart)
        savedCart = cart
        updateLiveData()
    }

    override fun insertAll(cart: Cart) {
        cartList.add(cart)
        savedCart = cart
        updateLiveData()
    }

    override fun delete(cart: Cart) {
        cartList.removeIf { it.goods.id == cart.goods.id }
        updateLiveData()
    }

    override fun getAll(callback: (Carts) -> Unit) {
        val totalQuantity = cartList.sumOf { it.quantity }
        callback(Carts(cartList.toList(), totalQuantity))
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<Carts> {
        val page = cartList.drop(offset).take(limit)
        val totalQuantity = cartList.sumOf { it.quantity }
        return MutableLiveData(Carts(page, totalQuantity))
    }

    override fun getAllItemsSize(): LiveData<Int> = sizeLiveData

    override fun getTotalQuantity(callback: (Int) -> Unit) {
        val total = cartList.sumOf { it.quantity }
        callback(total)
    }
}
