package woowacourse.shopping.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.InquiryHistory
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val inquiryHistoryRepository: InquiryHistoryRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _isSuccessAddToCart = MutableLiveData(false)
    val isSuccessAddToCart: LiveData<Boolean> get() = _isSuccessAddToCart

    private val _quantity = MutableLiveData<Quantity>()
    val quantity: LiveData<Quantity> get() = _quantity

    private val _lastViewedProduct = MutableLiveData<InquiryHistory>()
    val lastViewedProduct: LiveData<InquiryHistory> get() = _lastViewedProduct

    init {
        loadLastViewedProduct()
    }

    fun loadProduct(productId: Long) {
        _product.value = productRepository.find(productId)
        loadQuantity(productId)
        saveInquiryHistory()
    }

    fun addProductToCart(productId: Long) {
        val quantity = quantity.value ?: return
        thread {
            cartRepository.addCartItem(productId, quantity.count)
        }.join()
        _isSuccessAddToCart.value = true
    }

    fun increaseQuantity() {
        val quantity = quantity.value ?: return
        _quantity.value = quantity.inc()
    }

    fun decreaseQuantity() {
        val quantity = quantity.value ?: return
        _quantity.value = quantity.dec()
    }

    fun loadLastViewedProduct() {
        var lastViewedProduct: InquiryHistory? = null
        thread {
            lastViewedProduct = inquiryHistoryRepository.findLastViewedProduct()
        }.join()
        _lastViewedProduct.value = lastViewedProduct
    }

    private fun loadQuantity(productId: Long) {
        var cartItem: CartItem? = null
        thread {
            cartItem = cartRepository.find(productId)
        }.join()

        if (cartItem == null) {
            _quantity.value = Quantity(1)
            return
        } else {
            _quantity.value = Quantity(cartItem!!.quantity.count)
        }
    }

    private fun saveInquiryHistory() {
        val product = product.value ?: return
        thread {
            inquiryHistoryRepository.save(InquiryHistory(product, LocalDateTime.now()))
        }.join()
    }
}
