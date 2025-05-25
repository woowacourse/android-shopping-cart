package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product

class DetailViewModel(item: Product) : ViewModel() {
    private val _cartItem = MutableLiveData(CartItem(item.id, item, 1))
    val cartItem: LiveData<CartItem> = _cartItem

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    fun increaseQuantity() {
        _cartItem.value = _cartItem.value?.copy(quantity = _cartItem.value?.quantity?.plus(1) ?: 1)
//        _quantity.value = _quantity.value?.plus(1)
    }

    fun decreaseQuantity() {
        _cartItem.value = _cartItem.value?.copy(quantity = _cartItem.value?.quantity?.minus(1)?.coerceAtLeast(1) ?: 1)
//        _quantity.value = _quantity.value?.minus(1)?.coerceAtLeast(1)
    }
}
