package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Count
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModel(), DetailEventHandler, DetailCounterHandler {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _count = MutableLiveData<Count>()
    val count: LiveData<Count>
        get() = _count

    private val _moveBack = MutableLiveData<Event<Boolean>>()
    val moveBack: LiveData<Event<Boolean>>
        get() = _moveBack

    private val _addCartItem = MutableLiveData<Event<Long>>()
    val addCartItem: LiveData<Event<Long>>
        get() = _addCartItem

    init {
        loadProductData()
        loadCountData()
    }

    private fun loadProductData() {
        _product.postValue(shoppingRepository.findProductItem(productId))
    }

    private fun loadCountData() {
        val amount = fetchQuantity()
        val currentCount = Count(amount = amount)
        _count.value = currentCount
    }

    private fun fetchQuantity(): Int {
        return cartRepository.findOrNullWithProductId(productId)?.quantity ?: 1
    }

    fun createShoppingCartItem() {
        val product = product.value ?: return
        val quantity = count.value?.amount ?: return
        cartRepository.insert(product = product, quantity = quantity)
    }

    override fun addCartItem(productId: Long) {
        _addCartItem.postValue(Event(productId))
    }

    override fun moveBack() {
        _moveBack.postValue(Event(true))
    }

    override fun increaseCount() {
        _count.value?.increase()
        _count.value = _count.value
    }

    override fun decreaseCount() {
        _count.value?.decrease()
        _count.value = _count.value
    }
}
