package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(), QuantityClickListener {
    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> = _pageNumber

    private var size: Int = 0

    init {
        cartRepository.getSize { size = it }
    }

    fun isLastPage(): Boolean {
        val pageNumber = pageNumber.value ?: 1
        val size = pageNumber * 5
        return this.size <= size
    }

    private fun update() {
        val pageNumber = pageNumber.value ?: 1
        cartRepository.getPagedItems(5, (pageNumber - 1 ) * 5) { _cartItems.postValue(it) }
    }

    fun moveToPrevious() {
        if ((_pageNumber.value ?: 1) > 1) {
            _pageNumber.value = _pageNumber.value?.minus(1)
            update()
        }
    }

    fun moveToNext() {
        if (!isLastPage()) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            update()
        }
    }

    fun deleteProduct(product: Product) {
        cartRepository.removeById(product.id) {
            _cartItems.value = _cartItems.value?.filter { it.id != product.id }
        }
    }

    override fun onClickIncrease(cartItem: CartItem) {
        val newItem = cartItem.copy(quantity = cartItem.quantity + 1)
        updateQuantity(cartItem, newItem)
    }

    override fun onClickDecrease(cartItem: CartItem) {
        if (cartItem.quantity == 1) {
            cartRepository.removeById(cartItem.id) {
                val oldItems = _cartItems.value?.toMutableList()
                oldItems?.remove(cartItem)
                _cartItems.value = oldItems?.toList()
            }
            return
        }

        val newItem = cartItem.copy(quantity = (cartItem.quantity - 1).coerceAtLeast(1))
        updateQuantity(cartItem, newItem)
    }

    private fun updateQuantity(cartItem: CartItem, newItem: CartItem) {
        cartRepository.upsert(newItem) {
            val oldItems = _cartItems.value?.toMutableList()
            val index = oldItems?.indexOf(cartItem) ?: throw IllegalArgumentException("")
            oldItems[index] = newItem
            _cartItems.value = oldItems.toList()
        }
    }
}
