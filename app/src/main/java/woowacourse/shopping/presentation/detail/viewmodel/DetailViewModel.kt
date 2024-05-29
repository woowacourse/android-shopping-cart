package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.util.Event

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
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

    private val _recentProduct = MutableLiveData<Product>()
    val recentProduct: LiveData<Product>
        get() = _recentProduct

    private val _moveToRecentProduct = MutableLiveData<Event<Long>>()
    val moveToRecentProduct: LiveData<Event<Long>>
        get() = _moveToRecentProduct

    private val _showRecent = MutableLiveData<Boolean>(false)
    val showRecent: LiveData<Boolean>
        get() = _showRecent

    init {
        loadProductInformation(productId)
        loadCartItem(productId)
        getRecentProduct(productId, showRecent)
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
        _moveToRecentProduct.value = Event(id)
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

    private fun getRecentProduct(
        productId: Long,
        showRecent: Boolean,
    ) {
        val recentProductId = recentProductRepository.getMostRecentProductHistory()?.productId

        recentProductId?.let {
            _recentProduct.value = productRepository.fetchProduct(it)
            _showRecent.value = recentProductId == productId || !showRecent
        }

        recentProductRepository.setProductHistory(productId)
    }
}
