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
    private val product: Product,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private var shoppingCartQuantity = 0
    var lastViewedProduct: RecentProduct? = null
        private set

    private val _navigateEvent = MutableSingleLiveData<Unit>()
    val navigateEvent: SingleLiveData<Unit> get() = _navigateEvent

    private val _lastProductClickEvent = MutableSingleLiveData<Unit>()
    val lastProductClickEvent: SingleLiveData<Unit> get() = _lastProductClickEvent

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    init {
        loadQuantity()
        loadLastViewedProduct()
        updateRecentProduct()
    }

    override fun onAddToShoppingCartClick() {
        val addQuantity = quantity.value ?: 1
        cartProductRepository.updateQuantity(
            product.id,
            shoppingCartQuantity,
            shoppingCartQuantity + addQuantity,
        )
        _navigateEvent.setValue(Unit)
    }

    override fun onQuantityIncreaseClick(item: Product) {
        _quantity.value = (quantity.value ?: 1) + 1
    }

    override fun onQuantityDecreaseClick(item: Product) {
        val current = quantity.value ?: 1
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
        if (lastViewedProduct?.product?.id == product.id) {
            lastViewedProduct = null
        }
    }

    private fun updateRecentProduct() {
        val recentProduct = RecentProduct(product = product)
        recentProductRepository.replaceRecentProduct(recentProduct)
    }
}
