package woowacourse.shopping.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.InquiryHistory
import woowacourse.shopping.model.Product
import kotlin.concurrent.thread

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val inquiryRepository: InquiryHistoryRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _quantities = MutableLiveData<List<CartItemQuantity>>()
    val quantities: LiveData<List<CartItemQuantity>> get() = _quantities

    private val _isSeeMore = MutableLiveData(false)
    val isSeeMore: LiveData<Boolean> get() = _isSeeMore

    private val _cartQuantity = MutableLiveData<Int>()
    val cartQuantity: LiveData<Int> get() = _cartQuantity

    private val _inquiryHistories = MutableLiveData<List<InquiryHistory>>()
    val inquiryHistories: LiveData<List<InquiryHistory>> get() = _inquiryHistories

    private var page = 0

    init {
        loadPage()
        loadInquiryHistory()
    }

    fun loadPage() {
        val products = products.value ?: emptyList()
        _products.value = products + productRepository.findRange(page++, PAGE_SIZE)
        updateQuantities()
        updateQuantitySum()
    }

    fun loadInquiryHistory() {
        var inquiryHistories = emptyList<InquiryHistory>()
        thread {
            inquiryHistories = inquiryRepository.findAll()
        }.join()
        _inquiryHistories.value = inquiryHistories
    }

    fun addProductToCart(productId: Long) {
        thread {
            cartRepository.addProduct(productId)
        }.join()
        updateQuantities()
        updateQuantitySum()
    }

    fun deleteProductToCart(productId: Long) {
        runCatching {
            thread {
                cartRepository.deleteProduct(productId)
            }.join()
        }.onSuccess {
            updateQuantities()
            updateQuantitySum()
        }
    }

    fun updateSeeMoreStatus(
        lastPosition: Int,
        totalCount: Int?,
    ) {
        _isSeeMore.value = (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == totalCount
    }

    private fun updateQuantities() {
        val products = products.value ?: return
        var cartItemQuantities = emptyList<CartItemQuantity>()
        thread {
            cartItemQuantities = cartRepository.findQuantityOfCartItems(products)
        }.join()
        _quantities.value = cartItemQuantities
    }

    private fun updateQuantitySum() {
        val quantities = quantities.value ?: return
        _cartQuantity.value = quantities.sumOf { it.quantity.count }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
