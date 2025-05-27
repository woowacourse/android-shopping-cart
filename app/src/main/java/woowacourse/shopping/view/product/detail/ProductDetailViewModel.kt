package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.repository.CartProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.view.util.MutableSingleLiveData
import woowacourse.shopping.view.util.SingleLiveData

class ProductDetailViewModel(
    val product: Product,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private var shoppingCartQuantity = 0

    private val _lastViewedProduct = MutableLiveData<RecentProduct?>()
    val lastViewedProduct: LiveData<RecentProduct?> get() = _lastViewedProduct

    private val _quantity = MutableLiveData(INITIAL_QUANTITY)
    val quantity: LiveData<Int> get() = _quantity

    private val _totalPrice = MutableLiveData(product.price)
    val totalPrice: LiveData<Int> get() = _totalPrice

    private val _addToCartEvent = MutableSingleLiveData<Unit>()
    val addToCartEvent: SingleLiveData<Unit> get() = _addToCartEvent

    private val _lastProductClickEvent = MutableSingleLiveData<Unit>()
    val lastProductClickEvent: SingleLiveData<Unit> get() = _lastProductClickEvent

    init {
        loadQuantity()
        loadLastViewedProduct()
        updateRecentProduct()
    }

    override fun onAddToCartClick() {
        val addQuantity = quantity.value ?: return
        cartProductRepository.updateQuantity(
            product.id,
            shoppingCartQuantity,
            shoppingCartQuantity + addQuantity,
        ) {
            shoppingCartQuantity += addQuantity
            updateQuantity(INITIAL_QUANTITY)
        }
        _addToCartEvent.setValue(Unit)
    }

    override fun onQuantityIncreaseClick(item: Product) {
        updateQuantity((quantity.value ?: INITIAL_QUANTITY) + 1)
    }

    override fun onQuantityDecreaseClick(item: Product) {
        val quantity = (quantity.value ?: INITIAL_QUANTITY) - 1
        if (quantity > 0) {
            updateQuantity(quantity)
        }
    }

    override fun onLastProductClick() {
        _lastProductClickEvent.setValue(Unit)
    }

    private fun loadQuantity() {
        cartProductRepository.getQuantityByProductId(product.id) {
            shoppingCartQuantity = it ?: 0
        }
    }

    private fun loadLastViewedProduct() {
        recentProductRepository.getLastViewedProduct {
            _lastViewedProduct.postValue(it)
        }
    }

    private fun updateRecentProduct() {
        val recentProduct = RecentProduct(product = product)
        recentProductRepository.replaceRecentProduct(recentProduct) {}
    }

    private fun updateQuantity(newQuantity: Int) {
        _quantity.postValue(newQuantity)
        _totalPrice.postValue(newQuantity * product.price)
    }

    companion object {
        private const val INITIAL_QUANTITY = 1
    }
}
