package woowacourse.shopping.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.common.Event
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel

class ProductDetailViewModel(
    private val productId: Long,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
    private val lastSeenProductVisible: Boolean,
) : ViewModel() {
    private val _productUiModel = MutableLiveData<ProductUiModel>()
    val productUiModel: LiveData<ProductUiModel> get() = _productUiModel

    private val _productLoadError = MutableLiveData<Event<Unit>>()
    val productLoadError: LiveData<Event<Unit>> get() = _productLoadError

    private val _isSuccessAddCart = MutableLiveData<Event<Boolean>>()
    val isSuccessAddCart: LiveData<Event<Boolean>> get() = _isSuccessAddCart

    private val _lastRecentProduct = MutableLiveData<LastRecentProductUiModel>()
    val lastRecentProduct: LiveData<LastRecentProductUiModel> get() = _lastRecentProduct

    val isVisibleLastRecentProduct: LiveData<Boolean> =
        _lastRecentProduct.map { !lastSeenProductVisible && it.productId != _productUiModel.value?.productId }

    init {
        loadProduct()
        loadLastRecentProduct()
        saveRecentProduct()
    }

    private fun loadProduct() {
        val product = productRepository.findOrNull(productId)
        if (product == null) {
            _productLoadError.value = Event(Unit)
            return
        }
        _productUiModel.value = product.toProductUiModel()
    }

    private fun Product.toProductUiModel(): ProductUiModel {
        val cartItem = cartRepository.findOrNull(id) ?: return ProductUiModel.from(this)
        return ProductUiModel.from(this, cartItem.quantity)
    }

    private fun loadLastRecentProduct() {
        val lastRecentProduct = recentProductRepository.findLastOrNull() ?: return
        val product = productRepository.findOrNull(lastRecentProduct.productId) ?: return
        _lastRecentProduct.value = LastRecentProductUiModel(product.id, product.title)
    }

    private fun saveRecentProduct() {
        recentProductRepository.save(productId)
    }

    fun addCartProduct() {
        runCatching {
            val productUiModel = _productUiModel.value ?: return
            cartRepository.changeQuantity(productUiModel.productId, productUiModel.quantity)
        }.onSuccess {
            _isSuccessAddCart.value = Event(true)
        }.onFailure {
            _isSuccessAddCart.value = Event(false)
        }
    }

    fun increaseQuantity() {
        var quantity = _productUiModel.value?.quantity ?: return
        _productUiModel.value = _productUiModel.value?.copy(quantity = ++quantity)
    }

    fun decreaseQuantity() {
        var quantity = _productUiModel.value?.quantity ?: return
        _productUiModel.value = _productUiModel.value?.copy(quantity = --quantity)
    }
}
