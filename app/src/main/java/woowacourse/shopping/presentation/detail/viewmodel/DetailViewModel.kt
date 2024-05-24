package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.util.Event

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
    productId: Long,
    showRecent: Boolean,
) : ViewModel(), CartItemCountHandler {
    private val _productInformation: MutableLiveData<Product> = MutableLiveData()
    val productInformation: LiveData<Product>
        get() = _productInformation

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int>
        get() = _quantity

    private val _addComplete = MutableLiveData<Event<Long>>()
    val addComplete: LiveData<Event<Long>>
        get() = _addComplete

    private val _recentProductHistory = MutableLiveData<Product>()
    val recentProductHistory: LiveData<Product>
        get() = _recentProductHistory

    private val _moveToRecentProductHistory = MutableLiveData<Event<Long>>()
    val moveToRecentProductHistory: LiveData<Event<Long>>
        get() = _moveToRecentProductHistory

    private val _showRecent = MutableLiveData<Boolean>()
    val showRecent: LiveData<Boolean>
        get() = _showRecent

    init {
        loadProductInformation(productId)
        loadCartItem(productId)
        getProductHistory()
        addProductHistory(productId)
        loadShowRecent(showRecent)
    }

    fun loadShowRecent(showRecent: Boolean) {
        _showRecent.value = showRecent
    }

    fun getProductHistory() {
        val productId = productHistoryRepository.getMostRecentProductHistory()?.productId

        productId?.let {
            _recentProductHistory.value = productRepository.fetchProduct(it)
        }
    }

    fun addProductHistory(productId: Long) {
        productHistoryRepository.setProductHistory(productId)
    }

    fun loadProductInformation(id: Long) {
        _productInformation.value = productRepository.fetchProduct(id)
    }

    fun addToCart(
        id: Long,
        quantity: Int,
    ) {
        cartRepository.addCartItem(productId = id, quantity = quantity)

        val cart = cartRepository.fetchCartItem(id)

        cart?.let { _addComplete.value = Event(it.productId) }
    }

    fun moveToDetail(id: Long) {
        _moveToRecentProductHistory.value = Event(id)
    }

    override fun onCartItemAdd(id: Long) {
        _quantity.value = quantity.value?.plus(1)
    }

    override fun onCartItemMinus(id: Long) {
        if (quantity.value!! > 1) {
            _quantity.value = quantity.value?.minus(1)
        }
    }

    private fun loadCartItem(id: Long) {
        _quantity.value = cartRepository.fetchCartItem(id)?.quantity ?: 1
    }
}
