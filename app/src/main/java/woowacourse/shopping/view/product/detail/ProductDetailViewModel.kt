package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.view.common.MutableSingleLiveData
import woowacourse.shopping.view.common.SingleLiveData

class ProductDetailViewModel(
    val product: Product,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private var shoppingCartQuantity = 0
    var lastViewedProduct: RecentProduct? = null
        private set

    private val _addToCartEvent = MutableSingleLiveData<Unit>()
    val addToCartEvent: SingleLiveData<Unit> get() = _addToCartEvent

    private val _lastProductClickEvent = MutableSingleLiveData<Unit>()
    val lastProductClickEvent: SingleLiveData<Unit> get() = _lastProductClickEvent

    private val _quantity = MutableLiveData(INITIAL_QUANTITY)
    val quantity: LiveData<Int> get() = _quantity

    init {
        loadQuantity()
        loadLastViewedProduct()
        updateRecentProduct()
    }

    override fun onAddToCartClick() {
        val addQuantity = quantity.value ?: 0
        cartProductRepository.updateQuantity(
            product.id,
            shoppingCartQuantity,
            shoppingCartQuantity + addQuantity,
        )
        shoppingCartQuantity += addQuantity
        _addToCartEvent.setValue(Unit)
        _quantity.value = INITIAL_QUANTITY
    }

    override fun onQuantityIncreaseClick(item: Product) {
        _quantity.value = (quantity.value ?: INITIAL_QUANTITY) + 1
    }

    override fun onQuantityDecreaseClick(item: Product) {
        val current = quantity.value ?: INITIAL_QUANTITY
        if (current > 1) {
            _quantity.value = current - 1
        }
    }

    override fun onLastProductClick() {
        _lastProductClickEvent.setValue(Unit)
    }

    private fun loadQuantity() {
        shoppingCartQuantity = cartProductRepository.getQuantityByProductId(product.id) ?: 0
    }

    private fun loadLastViewedProduct() {
        lastViewedProduct = recentProductRepository.getLastProduct()
    }

    private fun updateRecentProduct() {
        val recentProduct = RecentProduct(product = product)
        recentProductRepository.replaceRecentProduct(recentProduct)
    }

    companion object {
        private const val INITIAL_QUANTITY = 1
    }
}
