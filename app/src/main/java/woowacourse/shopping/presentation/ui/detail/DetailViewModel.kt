package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModel(), DetailEventHandler {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _moveBack = MutableLiveData<Event<Boolean>>()
    val moveBack: LiveData<Event<Boolean>>
        get() = _moveBack

    private val _addCartItem = MutableLiveData<Event<Long>>()
    val addCartItem: LiveData<Event<Long>>
        get() = _addCartItem

    init {
        loadProductData()
    }

    private fun loadProductData() {
        _product.postValue(shoppingRepository.findProductItem(productId))
    }

    fun createShoppingCartItem() {
        val product = product.value ?: return
        cartRepository.insert(product = product, quantity = 1)
    }

    override fun addCartItem(productId: Long) {
        _addCartItem.postValue(Event(productId))
    }

    override fun moveBack() {
        _moveBack.postValue(Event(true))
    }
}
