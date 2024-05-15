package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val quantity: Int = 1

    private val _shoppingCart = MutableLiveData<List<CartItem>>()
    val shoppingCart: LiveData<List<CartItem>>
        get() = _shoppingCart

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        _shoppingCart.postValue(repository.findAll().items)
    }

    fun deleteItem(itemId: Long) {
        repository.delete(itemId)
        _shoppingCart.postValue(repository.findAll().items)
    }
}
