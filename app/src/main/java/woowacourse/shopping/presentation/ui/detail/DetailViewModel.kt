package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.ShoppingProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.event.Event

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val recentProductRepository: RecentProductRepository,
    val productId: Long,
) : ViewModel(), DetailEventHandler, DetailCounterHandler {
    private val _shoppingProduct = MutableLiveData<ShoppingProduct>()
    val shoppingProduct: LiveData<ShoppingProduct>
        get() = _shoppingProduct

    private val _recentProduct: MutableLiveData<RecentProduct> = MutableLiveData()
    val recentProduct: LiveData<RecentProduct>
        get() = _recentProduct

    private val _recentProductVisibility = MutableLiveData<Boolean>()
    val recentProductVisibility: LiveData<Boolean>
        get() = _recentProductVisibility

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _moveBack = MutableLiveData<Event<Boolean>>()
    val moveBack: LiveData<Event<Boolean>>
        get() = _moveBack

    private val _addCartItem = MutableLiveData<Event<Long>>()
    val addCartItem: LiveData<Event<Long>>
        get() = _addCartItem

    init {
        loadShoppingProductData()
        loadRecentProductData()
        checkRecentProductVisibility()
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

    private fun loadRecentProductData() {
        val recentProduct = recentProductRepository.loadSecondLatest() ?: return
        _recentProduct.value = recentProduct
    }

    fun createShoppingCartItem() {
        val product = shoppingProduct.value?.product ?: return
        val quantity = shoppingProduct.value?.quantity() ?: return
        cartRepository.insert(product = product, quantity = quantity)
    }

    private fun checkRecentProductVisibility() {
        _recentProductVisibility.value =
            !(recentProduct.value == null || recentProduct.value?.productId == productId)
    }

    override fun addCartItem(productId: Long) {
        _addCartItem.postValue(Event(productId))
    }

    override fun moveBack() {
        _moveBack.postValue(Event(true))
    }

    override fun onRecentProductClick(productId: Long) {
        _navigateToDetail.postValue(Event(productId))
    }

    override fun increaseCount(productId: Long) {
        _shoppingProduct.value?.increase()
        _shoppingProduct.value = _shoppingProduct.value
    }

    override fun decreaseCount(productId: Long) {
        _shoppingProduct.value?.decrease()
        _shoppingProduct.value = _shoppingProduct.value
    }
}
