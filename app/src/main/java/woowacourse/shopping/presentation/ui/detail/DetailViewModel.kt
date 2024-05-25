package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModel(), DetailEventHandler, DetailCounterHandler {
    private val _shoppingProduct = MutableLiveData<ShoppingProduct>()
    val shoppingProduct: LiveData<ShoppingProduct>
        get() = _shoppingProduct

    private val _moveBack = MutableLiveData<Event<Boolean>>()
    val moveBack: LiveData<Event<Boolean>>
        get() = _moveBack

    private val _addCartItem = MutableLiveData<Event<Long>>()
    val addCartItem: LiveData<Event<Long>>
        get() = _addCartItem

    init {
        loadShoppingProductData()
    }

    private fun loadShoppingProductData() {
        val shoppingProduct =
            ShoppingProduct(
                product = shoppingRepository.findProductItem(productId) ?: return,
                quantity = fetchQuantity(),
            )
        _shoppingProduct.value = shoppingProduct
    }

    private fun fetchQuantity(): Int {
        return cartRepository.findOrNullWithProductId(productId)?.quantity ?: 1
    }

    fun createShoppingCartItem() {
        val product = shoppingProduct.value?.product ?: return
        val quantity = shoppingProduct.value?.quantity ?: return
        cartRepository.insert(product = product, quantity = quantity)
    }

    override fun addCartItem(productId: Long) {
        _addCartItem.postValue(Event(productId))
    }

    override fun moveBack() {
        _moveBack.postValue(Event(true))
    }

    override fun increaseCount() {
        _shoppingProduct.value?.increase()
        _shoppingProduct.value = _shoppingProduct.value
    }

    override fun decreaseCount() {
        _shoppingProduct.value?.decrease()
        _shoppingProduct.value = _shoppingProduct.value
    }
}
