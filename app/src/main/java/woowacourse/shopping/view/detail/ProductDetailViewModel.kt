package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import woowacourse.shopping.utils.Event
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.CountActionHandler

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentViewedItemRepository: RecentViewedItemRepository,
    private val productId: Long,
    private val lastViewedProductSelected: Boolean,
) : ViewModel(), DetailActionHandler, CountActionHandler {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _cartItemSavedState: MutableLiveData<ProductDetailState> = MutableLiveData()
    val cartItemSavedState: LiveData<ProductDetailState> get() = _cartItemSavedState

    private val _navigateToBack = MutableLiveData<Event<Boolean>>()
    val navigateToBack: LiveData<Event<Boolean>> get() = _navigateToBack

    private val _navigateToLastViewedItem = MutableLiveData<Event<Long>>()
    val navigateToLastViewedItem: LiveData<Event<Long>> get() = _navigateToLastViewedItem

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    val totalPrice: LiveData<Int> =
        quantity.switchMap { quantityValue ->
            val price = product.value?.price ?: 1
            MutableLiveData(quantityValue * price)
        }

    val lastViewedProduct: Product by lazy {
        if (lastViewedProductSelected) {
            Product.INVALID_PRODUCT
        } else {
            recentViewedItemRepository.getLastViewedProduct()
        }
    }

    init {
        loadProductItem()
    }

    private fun loadProductItem() {
        _product.value = productRepository.getProduct(productId)
    }

    override fun onCloseButtonClicked() {
        var shouldUpdate = false
        product.value?.let {
            if (lastViewedProduct.id != it.id) {
                recentViewedItemRepository.saveRecentViewedItem(it)
                shouldUpdate = true
            }
        }
        _navigateToBack.value = Event(shouldUpdate)
    }

    override fun onAddCartButtonClicked() {
        val selectedProduct = product.value ?: throw NoSuchDataException()
        val selectedQuantity = quantity.value ?: 1
        runCatching {
            cartRepository.updateIncrementQuantity(selectedProduct, selectedQuantity)
        }.onSuccess {
            _cartItemSavedState.value = ProductDetailState.Success(selectedProduct.id, it)
        }.onFailure {
            _cartItemSavedState.value = ProductDetailState.Fail
        }
    }

    override fun onLastViewedItemClicked(productId: Long) {
        product.value?.let { recentViewedItemRepository.saveRecentViewedItem(it) }
        _navigateToLastViewedItem.value = Event(productId)
    }

    override fun onIncreaseQuantityButtonClicked(product: Product) {
        _quantity.value = _quantity.value?.plus(1)
    }

    override fun onDecreaseQuantityButtonClicked(product: Product) {
        _quantity.value = _quantity.value?.minus(1)?.coerceAtLeast(1)
    }
}
