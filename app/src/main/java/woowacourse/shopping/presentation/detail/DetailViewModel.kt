package woowacourse.shopping.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.home.DetailNavigationData
import woowacourse.shopping.presentation.home.products.QuantityListener
import woowacourse.shopping.presentation.util.Event
import woowacourse.shopping.presentation.util.StringResource
import kotlin.concurrent.thread

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    id: Long,
    lastViewedProductId: Long,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), QuantityListener {
    private val _productInformation: MutableLiveData<CartableProduct> = MutableLiveData()
    val productInformation: LiveData<CartableProduct>
        get() = _productInformation

    private val _message: MutableLiveData<Event<StringResource>> = MutableLiveData()
    val message: LiveData<Event<StringResource>>
        get() = _message

    private val _lastlyViewedProduct: MutableLiveData<RecentProduct> = MutableLiveData()
    val lastlyViewedProduct: LiveData<RecentProduct>
        get() = _lastlyViewedProduct

    private val _navigateToDetailEvent: MutableLiveData<Event<DetailNavigationData>> =
        MutableLiveData()
    val navigateToDetailEvent: LiveData<Event<DetailNavigationData>>
        get() = _navigateToDetailEvent

    init {
        val productId: Long? = savedStateHandle.get<Long>(KEY_PRODUCT_ID)
        if (productId == null) {
            savedStateHandle[KEY_PRODUCT_ID] = id
            loadProductInformation(id)
        } else {
            loadProductInformation(productId)
        }
        if (id != lastViewedProductId) {
            updateLastlyViewedProduct()
        }
    }

    fun updateCartStatus(productId: Long) {
        val targetProduct = productRepository.fetchProduct(productId)
        cartRepository.patchQuantity(
            targetProduct.product.id,
            productInformation.value?.quantity ?: return,
            productInformation.value?.cartItem
        )
        _message.value = Event(StringResource(R.string.message_add_to_cart_complete))

    }

    fun updateNavigationEvent(id: Long) {
        _navigateToDetailEvent.value = Event(DetailNavigationData(id, id))
    }

    fun updateHistory(id: Long) {
        productRepository.addProductHistory(ProductHistory(productId = id))
    }

    override fun onQuantityChange(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 0) return
        _productInformation.value =
            productInformation.value?.copy(
                cartItem = CartItem(
                    id = productInformation.value?.cartItem?.id,
                    productId = productId,
                    quantity = quantity
                ),
            )
    }

    private fun loadProductInformation(id: Long) {
        _productInformation.value = productRepository.fetchProduct(id)
    }

    private fun updateLastlyViewedProduct() {
        _lastlyViewedProduct.value =
            productRepository.fetchLatestHistory()
    }

    companion object {
        private const val KEY_PRODUCT_ID = "key_product_id"
    }
}
