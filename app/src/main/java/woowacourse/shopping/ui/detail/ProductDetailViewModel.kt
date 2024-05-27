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
import woowacourse.shopping.ui.utils.AddCartQuantityBundle

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

    val addCartQuantityBundle: LiveData<AddCartQuantityBundle> =
        _productUiModel.map {
            AddCartQuantityBundle(
                productId = it.productId,
                quantity = it.quantity,
                onIncreaseProductQuantity = { increaseQuantity() },
                onDecreaseProductQuantity = { decreaseQuantity() },
            )
        }

    private lateinit var product: Product

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
        product =
            runCatching {
                productRepository.find(productId)
            }.onFailure {
                _productLoadError.value = Event(Unit)
            }.getOrNull() ?: return

        _productUiModel.value = product.toProductUiModel()
    }

    private fun Product.toProductUiModel(): ProductUiModel {
        return runCatching { cartRepository.find(id) }
            .map { ProductUiModel.from(this, it.quantity) }
            .getOrElse { ProductUiModel.from(this) }
    }

    private fun loadLastRecentProduct() {
        val lastRecentProduct = recentProductRepository.findLastOrNull() ?: return
        val product = productRepository.find(lastRecentProduct.productId)
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

    private fun increaseQuantity() {
        var quantity = _productUiModel.value?.quantity ?: return
        _productUiModel.value = _productUiModel.value?.copy(quantity = ++quantity)
    }

    private fun decreaseQuantity() {
        var quantity = _productUiModel.value?.quantity ?: return
        _productUiModel.value = _productUiModel.value?.copy(quantity = --quantity)
    }
}
